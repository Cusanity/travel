package cn.cusanity.travel.dao;

import cn.cusanity.travel.domain.Category;
import cn.cusanity.travel.domain.PageBean;
import cn.cusanity.travel.domain.Route;

import java.util.List;

public interface CategoryDao {
    public List<Category> getCategoryList();

    public int routesNumberCountByCid(int cid);

    public List<Route> getRoutesByPage(int cid, int startItem, int itemPerPage);
}
