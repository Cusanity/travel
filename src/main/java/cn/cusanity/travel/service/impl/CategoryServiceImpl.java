package cn.cusanity.travel.service.impl;

import cn.cusanity.travel.dao.CategoryDao;
import cn.cusanity.travel.dao.impl.CategoryDaoImpl;
import cn.cusanity.travel.domain.*;
import cn.cusanity.travel.service.CategoryService;
import cn.cusanity.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao = new CategoryDaoImpl();

    public List<Category> getCategoryList() {
        //Not using redis
        //return categoryDao.getCategoryList();

        //Using redis
        List<Category> cl = new ArrayList<>();
        Jedis jedis = JedisUtil.getJedis();
//        Set<String> cnames = jedis.zrange("category", 0, -1);
        Set<Tuple> cnames = jedis.zrangeWithScores("category", 0, -1);
        if (cnames == null || cnames.size() == 0) {
            //Query database and save it in redis cache
            cl = categoryDao.getCategoryList();
            for (Category category : cl) {
                jedis.zadd("category", category.getCid(), category.getCname());
            }
        } else {
            for (Tuple cname : cnames) {
                Category category = new Category((int) cname.getScore(), cname.getElement());
                cl.add(category);
            }
        }
        return cl;
    }

    public PageBean<Route> getRoutes(int currentPage, int itemPerPage, int cid, String rname) {
        PageBean<Route> pb = new PageBean<>();
        int totalCount = categoryDao.routesCountByCid(cid, rname);
        pb.setTotalCount(totalCount);
        //Set Total Page
        int totalPage = totalCount % itemPerPage == 0 ? totalCount / itemPerPage : totalCount / itemPerPage + 1;
        pb.setTotalPage(totalPage);
        pb.setCurrentPage(currentPage);
        pb.setItemPerPage(itemPerPage);
        //Set route list
        int startItem = (currentPage - 1) * itemPerPage;
        List<Route> routesByPage = categoryDao.getRoutesByPage(cid, rname, startItem, itemPerPage);
        pb.setItemList(routesByPage);
        return pb;
    }

    @Override
    public Route findARoute(int rid) {
        Route route = categoryDao.findARoute(rid);
        //Get route images
        List<RouteImg> imgsByRid = categoryDao.findImgsByRid(rid);
        route.setRouteImgList(imgsByRid);
        //Get seller information
        Seller seller = categoryDao.findSellerByRid(route.getSid());
        route.setSeller(seller);
        Category category = categoryDao.findCategoryByCid(route.getCid());
        route.setCategory(category);
        int favCount = categoryDao.favCountByRid(rid);
        route.setCount(favCount);
        return route;
    }

    @Override
    public boolean findFavByRidCid(int rid, int uid) {
        Favorite fav = categoryDao.findFavByRidUid(rid, uid);
        return fav != null;
    }

    /**
     * @return new fav number
     */
    @Override
    public int updateFav(int rid, int cid, boolean add) {
        categoryDao.updateFav(rid, cid, add);
        return categoryDao.favCountByRid(rid);
    }

    @Override
    public PageBean<Route> getFavsByUid(int uid) {
        PageBean<Route> pb = new PageBean<>();
        List<Route> favs = categoryDao.getFavsByUid(uid);
        for (Route route :favs) {
            Route tempRoute = categoryDao.findARoute(route.getRid());
            route.setRname(tempRoute.getRname());
            route.setPrice(tempRoute.getPrice());
            route.setRimage(tempRoute.getRimage());
        }
        pb.setItemList(favs);
        return pb;
    }
}