package cn.cusanity.travel.service;

import cn.cusanity.travel.domain.Category;
import cn.cusanity.travel.domain.PageBean;
import cn.cusanity.travel.domain.Route;
import cn.cusanity.travel.domain.RouteImg;

import java.util.List;

public interface CategoryService {

    List<Category> getCategoryList();

    PageBean<Route> getRoutes(int currentPage, int itemPerPage, int cid, String rname);

    Route findARoute(int rid);

}
