package cn.cusanity.travel.service;

import cn.cusanity.travel.domain.*;

import java.util.List;

public interface CategoryService {

    List<Category> getCategoryList();

    PageBean<Route> getRoutes(int currentPage, int itemPerPage, int cid, String rname);

    Route findARoute(int rid);

    boolean findFavByRidCid(int rid, int cid);

    int updateFav(int rid, int cid, boolean add);

    PageBean<Route> getFavsByUid(int uid);

    PageBean<Route> getTopFavs(int top_num);
}
