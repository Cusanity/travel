package cn.cusanity.travel.service.impl;

import cn.cusanity.travel.dao.CategoryDao;
import cn.cusanity.travel.dao.impl.CategoryDaoImpl;
import cn.cusanity.travel.domain.Category;
import cn.cusanity.travel.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> getCategoryList() {
        return categoryDao.getCategoryList();
    }
}
