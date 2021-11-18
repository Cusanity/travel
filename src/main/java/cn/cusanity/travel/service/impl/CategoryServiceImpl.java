package cn.cusanity.travel.service.impl;

import cn.cusanity.travel.dao.CategoryDao;
import cn.cusanity.travel.dao.impl.CategoryDaoImpl;
import cn.cusanity.travel.domain.Category;
import cn.cusanity.travel.service.CategoryService;
import cn.cusanity.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> getCategoryList() {
        //Not using redis
        //return categoryDao.getCategoryList();

        //Using redis
        List<Category> cl = new ArrayList<>();
        Jedis jedis = JedisUtil.getJedis();
        Set<String> cnames = jedis.zrange("category", 0, -1);
        if(cnames == null || cnames.size() == 0){
            System.out.println("database");
            //Query database and save it in redis cache
            cl = categoryDao.getCategoryList();
            for (Category category : cl) {
                jedis.zadd("category", category.getCid(), category.getCname());
            }
        }else{
            System.out.println("redis");
            for (String cname: cnames) {
                Category category = new Category(cname);
                cl.add(category);
            }
        }
        return cl;
    }
}
