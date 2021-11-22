package cn.cusanity.travel.dao;

import cn.cusanity.travel.domain.*;

import java.util.List;

public interface CategoryDao {
    List<Category> getCategoryList();

    int routesCountByCid(int cid, String rname);

    List<Route> getRoutesByPage(int cid, String rname, int startItem, int itemPerPage);

    Route findARoute(int rid);

    List<RouteImg> findImgsByRid(int rid);

    Seller findSellerByRid(int sid);

    Category findCategoryByCid(int cid);

    Favorite findFavByRidUid(int rid, int uid);

    int favCountByRid(int rid);

    void updateFav(int rid, int uid, boolean add);

    List<Route> getFavsByUid(int uid);

    List<Route> getTopFavs(int top_num);
}
