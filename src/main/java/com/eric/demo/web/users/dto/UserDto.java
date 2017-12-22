package com.eric.demo.web.users.dto;

import org.hibernate.validator.constraints.NotBlank;

public class UserDto {

    @NotBlank(message = "{com.ziroom.kaka.push.common.entity.UserAccount.userName.null}")
    private String userName;

    @NotBlank(message = "{com.ziroom.kaka.push.common.entity.UserAccount.password.null}")
    private String password;

    public String getUserName() {
        return userName;
    }

    public UserDto setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDto setPassword(String password) {
        this.password = password;
        return this;
    }
}
