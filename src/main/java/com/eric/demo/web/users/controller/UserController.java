package com.eric.demo.web.users.controller;

import com.eric.demo.commons.util.Check;
import com.eric.demo.commons.util.MD5Util;
import com.eric.demo.commons.util.ResponseVo;
import com.eric.demo.commons.util.SOAResParseUtil;
import com.eric.demo.commons.validator.BaseConst;
import com.eric.demo.commons.validator.DataTransferObject;
import com.eric.demo.commons.validator.ParamCheckLogic;
import com.eric.demo.web.users.domain.User;
import com.eric.demo.web.users.dto.UserDto;
import com.eric.demo.web.users.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户控制器
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ParamCheckLogic paramCheckLogic;

    @RequestMapping("login")
    @ResponseBody
    public ResponseVo login(HttpSession session, UserDto userDto) {
        //参数校验
        DataTransferObject dataTransferObject = paramCheckLogic.checkObjParamValidate(userDto);
        if (!SOAResParseUtil.checkDTO(dataTransferObject)) {
            return ResponseVo.responseError(dataTransferObject.getMsg());
        }
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setPassword(MD5Util.string2MD5(userDto.getPassword()));
        List<User> userList = userService.findByVo(user);
        if (Check.NuNCollection(userList)) {
            return ResponseVo.responseError("用户信息不存在");
        }
        session.setAttribute(BaseConst.USER_SESSION_KEY, userList.get(0));

        return ResponseVo.responseOk(null);
    }

}
