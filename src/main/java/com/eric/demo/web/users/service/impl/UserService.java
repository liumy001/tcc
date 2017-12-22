package com.eric.demo.web.users.service.impl;

import com.eric.demo.commons.dao.BaseDao;
import com.eric.demo.commons.service.BaseServiceImpl;
import com.eric.demo.web.users.dao.UserDao;
import com.eric.demo.web.users.domain.User;
import com.eric.demo.web.users.domain.UserCriteria;
import com.eric.demo.web.users.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService extends BaseServiceImpl<User, UserCriteria> implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    protected BaseDao<User, UserCriteria, String> getDao() {
        return userDao;
    }
}
