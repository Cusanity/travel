package cn.cusanity.travel.service.impl;

import cn.cusanity.travel.dao.CategoryDao;
import cn.cusanity.travel.dao.impl.CategoryDaoImpl;
import cn.cusanity.travel.domain.Category;
import cn.cusanity.travel.domain.PageBean;
import cn.cusanity.travel.domain.Route;
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
            System.out.println("database");
            //Query database and save it in redis cache
            cl = categoryDao.getCategoryList();
            for (Category category : cl) {
                jedis.zadd("category", category.getCid(), category.getCname());
            }
        } else {
            System.out.println("redis");
            for (Tuple cname : cnames) {
                Category category = new Category((int) cname.getScore(), cname.getElement());
                cl.add(category);
            }
        }
        return cl;
    }

    public PageBean<Route> getRoutes(int currentPage, int itemPerPage, int cid) {
        PageBean<Route> pb = new PageBean<>();
        int totalCount = categoryDao.routesNumberCountByCid(cid);
        pb.setTotalCount(totalCount);
        //Set Total Page
        int totalPage = totalCount % itemPerPage == 0 ? totalCount / itemPerPage : totalCount / itemPerPage + 1;
        pb.setTotalPage(totalPage);
        pb.setCurrentPage(currentPage);
        pb.setItemPerPage(itemPerPage);
        //Set route list
        int startItem = (currentPage - 1) * itemPerPage;
        List<Route> routesByPage = categoryDao.getRoutesByPage(cid, startItem, itemPerPage);
        pb.setItemList(routesByPage);
        return pb;
    }
}