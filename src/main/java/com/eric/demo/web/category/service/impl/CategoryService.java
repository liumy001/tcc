package com.eric.demo.web.category.service.impl;

import com.eric.demo.commons.dao.BaseDao;
import com.eric.demo.commons.service.BaseServiceImpl;
import com.eric.demo.web.category.dao.CategoryDao;
import com.eric.demo.web.category.domain.Category;
import com.eric.demo.web.category.domain.CategoryCriteria;
import com.eric.demo.web.category.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CategoryService extends BaseServiceImpl<Category, CategoryCriteria> implements ICategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    protected BaseDao<Category, CategoryCriteria, String> getDao() {
        return this.categoryDao;
    }
}
