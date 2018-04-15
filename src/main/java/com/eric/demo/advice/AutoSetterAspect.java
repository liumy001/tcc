package com.eric.demo.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

@Aspect
@Component
public class AutoSetterAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoSetterAspect.class);

    @Around("execution(* com.eric.demo.commons.dao.BaseDao.insert*(..)))|| execution(* com.eric.demo.commons.dao.BaseDao.update*(..))) || execution(* com.eric.demo.commons.dao.BaseDao.logicDeleteById*(..))) ")
    public Object process(ProceedingJoinPoint pjd) throws Throwable {
        try {
            MethodSignature signature = (MethodSignature) pjd.getSignature();
            Method targetMethod = signature.getMethod();

            Object[] args = pjd.getArgs();
            for (Object arg : args) {
                // 保存时自动设置创建时间和创建人
                if (targetMethod.getName().startsWith("insert")) {
                    // 设置创建时间
                    try {
                        processSave(arg);
                    } catch (Exception e) {
                    }
                }
                //更新时间自动注入
                if (targetMethod.getName().startsWith("update") || targetMethod.getName().startsWith("logicDeleteById")) {
                    // 设置创建时间
                    try {
                        processUpdate(arg);
                    } catch (Exception e) {
                    }
                }
            }
            return pjd.proceed(args);
        } catch (Exception e) {
            throw e;
        } finally {

        }
    }

    private void processUpdate(Object arg) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method getLastModifiedDate = arg.getClass().getMethod("getLastModifiedDate", Date.class);
        if (getLastModifiedDate.invoke(arg, null) == null) {
            Method setLastModifiedDate = arg.getClass().getMethod("setLastModifiedDate", Date.class);
            setLastModifiedDate.invoke(arg, new Date());
        }
        Method getUpdater = arg.getClass().getMethod("getLastModifiedBy");
        Object updater = getUpdater.invoke(arg, null);
        if (updater == null) {
            Method setUpdater = arg.getClass().getMethod("setLastModifiedBy", String.class);
            setUpdater.invoke(arg, "system");
        }
    }

    private void processSave(Object arg) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method setCreateDate = arg.getClass().getMethod("setCreatedDate", Date.class);
        setCreateDate.invoke(arg, new Date());
        Method setLastModifiedDate = arg.getClass().getMethod("setLastModifiedDate", Date.class);
        setLastModifiedDate.invoke(arg, new Date());
        Method setIsDel = arg.getClass().getMethod("setIsDel", Integer.class);
        setIsDel.invoke(arg, 0);
        Method setCreator = arg.getClass().getMethod("setCreatedBy", String.class);
        setCreator.invoke(arg, "system");
        Method setUpdater = arg.getClass().getMethod("setLastModifiedBy", String.class);
        setUpdater.invoke(arg, "system");
    }
}
