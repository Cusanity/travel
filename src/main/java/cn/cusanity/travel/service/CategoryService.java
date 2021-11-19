package cn.cusanity.travel.service;

import cn.cusanity.travel.domain.Category;
import cn.cusanity.travel.domain.PageBean;
import cn.cusanity.travel.domain.Route;

import java.util.List;

public interface CategoryService {

    public List<Category> getCategoryList();

    public PageBean<Route> getRoutes(int currentPage, int itemPerPage, int cid);

}
