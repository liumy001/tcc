package com.eric.demo.web.users.dao;

import com.eric.demo.commons.dao.BaseDao;
import com.eric.demo.web.users.domain.User;
import com.eric.demo.web.users.domain.UserCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

/**
 * @数表名称 t_user
 * @开发日期 2017-12-22 16:16:07
 * @开发作者 by:liumy 
 */
public interface UserDao extends BaseDao<User, UserCriteria, String> {
}