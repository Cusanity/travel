package cn.cusanity.travel.dao;

import cn.cusanity.travel.domain.Category;
import cn.cusanity.travel.domain.Route;
import cn.cusanity.travel.domain.RouteImg;
import cn.cusanity.travel.domain.Seller;

import java.util.List;

public interface CategoryDao {
    List<Category> getCategoryList();

    int routesCountByCid(int cid, String rname);

    List<Route> getRoutesByPage(int cid, String rname, int startItem, int itemPerPage);

    Route findARoute(int rid);

    List<RouteImg> findImgsByRid(int rid);

    Seller findSellerByRid(int sid);

    Category findCategoryByCid(int cid);
}
