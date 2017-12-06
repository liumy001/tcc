package com.eric.demo.api.user.dao;

import com.eric.demo.api.user.domain.User;
import com.eric.demo.api.user.domain.UserCriteria;
import com.eric.demo.commons.dao.BaseDao;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

/**
 * @数表名称 t_user
 * @开发日期 2017-12-06 12:52:47
 * @开发作者 by:liumy 
 */
public interface UserDao extends BaseDao<User, UserCriteria, String> {
}