/**
 * @FileName: WebConfig.java
 * @Package: com.ziroom.kaka.kaka.config
 * @author wurt2
 * @created 2017/5/17 11:08
 * <p>
 * Copyright 2015 ziroom
 */
package com.eric.demo.config;

import com.eric.demo.commons.interceptor.AccessLogInterceptor;
import com.eric.demo.commons.interceptor.AllInterceptor;
import com.eric.demo.commons.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改�?			修改内容
 * </PRE>
 *
 * @author wurt2
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public AccessLogInterceptor getAccessLogInterceptor() {
        return new AccessLogInterceptor();
    }

    @Bean
    public AllInterceptor getAllInterceptor() {
        return new AllInterceptor();
    }

    @Bean
    public TokenInterceptor getTokenInterceptor() {
        return new TokenInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAccessLogInterceptor()).excludePathPatterns("/error*//**").addPathPatterns("/**");
        registry.addInterceptor(getAllInterceptor()).excludePathPatterns("/error*//**").addPathPatterns("/**");
        registry.addInterceptor(getTokenInterceptor()).excludePathPatterns("/error*//**").addPathPatterns("/**");
    }


}
