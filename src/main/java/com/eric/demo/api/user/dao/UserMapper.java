package com.eric.demo.api.user.dao;

import com.eric.demo.api.user.domain.User;
import com.eric.demo.api.user.domain.UserCriteria;
import java.util.List;

import com.eric.demo.commons.dao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UserMapper extends BaseDao<User,UserCriteria,String>{
    int countByExample(UserCriteria example);

    int deleteByExample(UserCriteria example);

    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExampleWithRowbounds(UserCriteria example, RowBounds rowBounds);

    List<User> selectByExample(UserCriteria example);

    User selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserCriteria example);

    int updateByExample(@Param("record") User record, @Param("example") UserCriteria example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}