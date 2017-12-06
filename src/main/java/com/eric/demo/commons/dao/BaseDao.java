package com.eric.demo.commons.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface BaseDao<T extends Serializable, E, ID extends Serializable> {
    int countByExample(E example);

    int deleteByExample(E example);

    int deleteByPrimaryKey(ID id);

    int insert(T record);

    int insertSelective(T record);

    List<T> selectByExampleWithRowbounds(E example, RowBounds rowBounds);

    List<T> selectByExample(E example);

    T selectByPrimaryKey(ID id);

    int updateByExampleSelective(@Param("record") T record, @Param("example") E example);

    int updateByExample(@Param("record") T record, @Param("example") E example);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);

    int logicDeleteById(ID id);

    List<T> findList(Map<String, Object> paramMap);

    List<T> findByVo(T entity);
}
