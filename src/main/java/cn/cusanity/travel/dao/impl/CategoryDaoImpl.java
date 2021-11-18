package cn.cusanity.travel.dao.impl;

import cn.cusanity.travel.dao.CategoryDao;
import cn.cusanity.travel.domain.Category;
import cn.cusanity.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate((JDBCUtils.getDataSource()));

    @Override
    public List<Category> getCategoryList() {
        String sql = "select * from tab_category";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }
}