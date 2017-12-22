package com.eric.demo.web.category.dao;

import com.eric.demo.commons.dao.BaseDao;
import com.eric.demo.web.category.domain.Category;
import com.eric.demo.web.category.domain.CategoryCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

/**
 * @数表名称 t_category
 * @开发日期 2017-12-22 15:55:12
 * @开发作者 by:liumy 
 */
public interface CategoryDao extends BaseDao<Category, CategoryCriteria, String> {
}