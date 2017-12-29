package com.eric.demo.web.users.controller;

import com.eric.demo.commons.util.Check;
import com.eric.demo.commons.validator.BaseConst;
import com.eric.demo.web.users.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping("/")
    public String index(HttpSession session) {
        User user = (User) session.getAttribute(BaseConst.USER_SESSION_KEY);
        if (Check.NuNObj(user)) {
            return "login/login";
        } else {
            return "index/index";
        }
    }

    @RequestMapping("index")
    public String toIndex() {
        return "index/index";
    }


}
