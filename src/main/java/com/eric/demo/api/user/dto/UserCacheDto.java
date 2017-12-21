package com.eric.demo.api.user.dto;

import com.eric.demo.api.user.domain.User;

import java.io.Serializable;
import java.util.List;

public class UserCacheDto implements Serializable {

    private List<User> userList;

    public List<User> getUserList() {
        return userList;
    }

    public UserCacheDto setUserList(List<User> userList) {
        this.userList = userList;
        return this;
    }
}
