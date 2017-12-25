package com.eric.demo.web.users.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

public class RegisterDto {

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[0-9a-zA-Z]{6,32}$", message = "用户名长度为6-32字母或者数字组成")
    private String userName;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$", message = "密码必须由8-16位字母或数字组成")
    private String password;

    @NotBlank(message = "邮箱不能为空")
    @Pattern(regexp = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", message = "邮箱格式错误")
    private String email;

    private String extEmail;

    @NotBlank(message = "昵称不能为空")
    @Length(min = 1, max = 30, message = "昵称长度错误")
    @Pattern(regexp = "^[0-9a-zA-Z\\u4e00-\\u9fa5]+$", message = "昵称格式为字母数字中文")
    private String nickName;

    public String getUserName() {
        return userName;
    }

    public RegisterDto setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegisterDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getExtEmail() {
        return extEmail;
    }

    public RegisterDto setExtEmail(String extEmail) {
        this.extEmail = extEmail;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public RegisterDto setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }
}
