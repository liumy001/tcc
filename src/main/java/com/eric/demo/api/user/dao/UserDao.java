package com.eric.demo.api.user.dao;

import com.eric.demo.api.user.domain.User;
import com.eric.demo.api.user.domain.UserCriteria;
import com.eric.demo.commons.dao.BaseDao;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UserDao extends BaseDao<User, UserCriteria, String> {

}