package cn.cusanity.travel.dao.impl;

import cn.cusanity.travel.dao.CategoryDao;
import cn.cusanity.travel.domain.Category;
import cn.cusanity.travel.domain.Route;
import cn.cusanity.travel.domain.RouteImg;
import cn.cusanity.travel.domain.Seller;
import cn.cusanity.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate((JDBCUtils.getDataSource()));

    @Override
    public List<Category> getCategoryList() {
        String sql = "select * from tab_category";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }

    @Override
    public int routesCountByCid(int cid, String rname) {
        String sql = "select count(*) from tab_route where 1=1 ";
        ArrayList<Object> params = new ArrayList<>();
        if (cid > 0) {
            sql += " and cid = ? ";
            params.add(cid);
        }
        if (rname != null && rname.length() > 0) {
            sql += " and rname like ? ";
            params.add("%" + rname + "%");
        }
        return jdbcTemplate.queryForObject(sql, Integer.class, params.toArray());
    }

    @Override
    public List<Route> getRoutesByPage(int cid, String rname, int startItem, int itemPerPage) {
        String sql = "select * from tab_route where 1=1 ";
        ArrayList<Object> params = new ArrayList<>();
        if (cid > 0) {
            sql += " and cid = ? ";
            params.add(cid);
        }
        if (rname != null && rname.length() > 0) {
            sql += " and rname like ? ";
            params.add("%" + rname + "%");
        }
        sql += " limit ?, ? ";
        params.add(startItem);
        params.add(itemPerPage);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class), params.toArray());
    }

    @Override
    public Route findARoute(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Route.class), rid);
    }

    @Override
    public List<RouteImg> findImgsByRid(int rid) {
        String sql = "select * from tab_route_img where rid = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RouteImg.class), rid);
    }

    @Override
    public Seller findSellerByRid(int sid) {
        String sql = "select * from tab_seller where sid = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Seller.class), sid);
    }

    @Override
    public Category findCategoryByCid(int cid) {
        String sql = "select * from tab_category where cid = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Category.class), cid);
    }
}