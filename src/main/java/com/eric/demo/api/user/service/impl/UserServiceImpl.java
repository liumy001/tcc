package com.eric.demo.api.user.service.impl;

import java.util.List;

import com.eric.demo.api.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.eric.demo.api.user.domain.User;
import com.eric.demo.api.user.domain.UserCriteria;
import com.eric.demo.api.user.service.UserService;
import com.eric.demo.commons.dao.BaseDao;
import com.eric.demo.commons.service.BaseServiceImpl;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, UserCriteria>
        implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findOneByUsername(String username) {
        Assert.notNull(username);
        UserCriteria userCriteria = new UserCriteria();
        userCriteria.or().andUsernameEqualTo(username);
        List<User> userList = userDao.selectByExample(userCriteria);
        if (userList.size() > 0)
            return userList.get(0);
        else
            return null;
    }

    @Override
    public void changePassword(String newPassword) {
        Assert.notNull(newPassword);

    }

    @Override
    protected BaseDao<User, UserCriteria, String> getDao() {
        return userDao;
    }
}
