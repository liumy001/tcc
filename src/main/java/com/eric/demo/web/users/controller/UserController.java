package com.eric.demo.web.users.controller;

import com.eric.demo.commons.validator.DataTransferObject;
import com.eric.demo.commons.validator.ParamCheckLogic;
import com.eric.demo.web.users.dto.UserDto;
import com.eric.demo.web.users.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 用户控制器
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private IUserService userService;
/*
    @Autowired
    private ParamCheckLogic paramCheckLogic;*/

    @RequestMapping("login")
    @ResponseBody
    public String login(HttpSession session, UserDto userDto) {
        //DataTransferObject dataTransferObject= paramCheckLogic.checkObjParamValidate(userDto);
        return null;
    }

}
