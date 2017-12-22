package com.eric.demo.advice;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CommonLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoSetterAspect.class);

    @Around("@annotation(com.eric.demo.commons.annotation.CommonLog)")
    public Object process(ProceedingJoinPoint pjd) throws Throwable {
        String prx = pjd.getThis().toString().substring(0, pjd.getThis().toString().indexOf("@")) + ".";
        LOGGER.info("方法名：" + prx + pjd.getSignature().getName() + ",入参：" + JSONObject.toJSONString(pjd.getArgs()));
        long startTime = System.currentTimeMillis();
        Object obj = pjd.proceed(pjd.getArgs());
        long endTime = System.currentTimeMillis();
        LOGGER.info("方法名：" + prx + pjd.getSignature().getName() + ",出参：" + JSONObject.toJSONString(obj) + "\n累计耗时|" + (endTime - startTime));
        return obj;
    }

}
