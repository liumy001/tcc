package com.eric.demo.commons.interceptor;

import com.eric.demo.commons.util.Check;
import com.eric.demo.commons.validator.BaseConst;
import com.eric.demo.web.users.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zy
 * @Date 2016-06-21 登陆拦截器
 */
@PropertySource("system.properties")
public class AllInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AllInterceptor.class);

    @Value(value = "${login.url}")
    private String loginUrl;

    private static String urls = "/,/user/toReg,/user/login,/user/register,/user/sendEmail";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String scheme = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
        LOGGER.info(scheme);
        request.setAttribute("basePath", scheme);

        String url = request.getRequestURI().toString();
        LOGGER.info("请求url：" + url);
        String[] urlSplit = urls.split(",");
        for (int i = 0; i < urlSplit.length; i++) {
            String matchString = urlSplit[i];
            if (matchString.contains(url)) {
                LOGGER.info("请求url过滤成功：" + url+":"+matchString);
                return super.preHandle(request, response, handler);
            }
        }
        User user = (User) request.getSession().getAttribute(BaseConst.USER_SESSION_KEY);
        if (Check.NuNObj(user)) {
            //重定向到登录页
            response.sendRedirect(loginUrl);
            return false;
        }
        return super.preHandle(request, response, handler);
    }

}
