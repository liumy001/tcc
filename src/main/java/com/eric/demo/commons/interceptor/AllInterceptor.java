package com.eric.demo.commons.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    private static String urls = "/login.do,/index.do,/loginPage.do";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI().toString();
        String[] urlSplit = urls.split(",");
        for (int i = 0; i < urlSplit.length; i++) {
            String matchString = urlSplit[i];
            LOGGER.info("url:"+matchString);
            if (url.matches(".*" + matchString + ".*")) {
                return super.preHandle(request, response, handler);
            }
        }

        return super.preHandle(request, response, handler);
    }

}
