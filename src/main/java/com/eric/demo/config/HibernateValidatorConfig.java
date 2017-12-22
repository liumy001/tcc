package com.eric.demo.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author wurt2
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class HibernateValidatorConfig {

    @Bean
    public Validator validator(LocalValidatorFactoryBean localValidatorFactoryBean) {
        return localValidatorFactoryBean.getValidator();
    }

    @Bean
    public ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource(){
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource=new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setBasenames("classpath:message/validator.properties","classpath:org/hibernate/validator/ValidationMessages","classpath:message/fw-order-message.properties");
        reloadableResourceBundleMessageSource.setDefaultEncoding("utf-8");
        reloadableResourceBundleMessageSource.setUseCodeAsDefaultMessage(false);
        reloadableResourceBundleMessageSource.setCacheSeconds(60);

        return reloadableResourceBundleMessageSource;
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean(ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource){
        LocalValidatorFactoryBean localValidatorFactoryBean=new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setProviderClass(HibernateValidator.class);
        localValidatorFactoryBean.setValidationMessageSource(reloadableResourceBundleMessageSource);

        return localValidatorFactoryBean;
    }





}
