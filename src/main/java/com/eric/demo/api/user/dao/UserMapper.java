package com.eric.demo.api.user.dao;

import com.eric.demo.api.user.domain.User;
import com.eric.demo.api.user.domain.UserCriteria;
import java.util.List;

import com.eric.demo.commons.dao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UserMapper extends BaseDao<User,UserCriteria,String>{

}