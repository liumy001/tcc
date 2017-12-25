/*
 * Copyright (c) 2016. ziroom.com.
 */
package com.eric.demo.commons.validator;

import com.alibaba.fastjson.JSON;
import com.eric.demo.commons.util.Check;
import com.eric.demo.commons.util.SOAResParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>参数检测</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @version 1.0
 * @since 1.0
 */
@Component
public class ParamCheckLogic {

    private static final int errorCode=1;

    private static final String PARAM_NULL="校验对象为空错误";

    private MessageSource messageSource;


    @Autowired
    private Validator validator;

    public ParamCheckLogic() {

    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public DataTransferObject checkParamNull(String paramStr) {
        DataTransferObject dto = null;
        //参数是否为空
        if (Check.NuNStrStrict(paramStr)) {
            dto = new DataTransferObject();
            dto.setErrCode(errorCode);
            dto.setMsg(PARAM_NULL);
            return dto;
        }
        return dto;
    }

    /**
     * 校验参数，并依据validator校验
     *
     * @param jsonStr
     * @param clazz
     *
     * @return
     */
    public <T> ValidateResult<T> checkParamValidate(String jsonStr, Class<T> clazz) {
        return checkParamValidate(jsonStr, clazz, false);
    }

    /**
     * 校验参数，并依据validator校验
     *
     * @param jsonStr
     * @param clazz
     * @param isNUll
     *
     * @return
     */
    public <T> ValidateResult<T> checkParamValidate(String jsonStr, Class<T> clazz, boolean isNUll) {
        DataTransferObject dto = null;
        T t = JSON.parseObject(jsonStr, clazz);
        //参数是否为空
        if (isNUll && Check.NuNStrStrict(jsonStr)) {
            return new ValidateResult<T>(t);
        }
        dto = checkParamNull(jsonStr);
        if (!Check.NuNObj(dto)) {
            return new ValidateResult<T>(dto);
        }
        //valiator校验
        dto = checkObjParamValidate(t);
        if (!Check.NuNObj(dto)) {
            return new ValidateResult<T>(dto);
        }
        return new ValidateResult<T>(t);
    }

    /**
     * 对象校验，依据validator
     * use case
     * 派单前做的工单校验
     *
     * @param t
     * @param <T>
     *
     * @return
     */
    public <T> DataTransferObject checkObjParamValidate(T t) {
        DataTransferObject dto = null;
        //参数是否为空
        if (Check.NuNObj(t)) {
            dto = new DataTransferObject();
            dto.setErrCode(errorCode);
            dto.setMsg(PARAM_NULL);
            return dto;
        }
        //是否符合validator校验
        StringBuilder errorMsg = new StringBuilder();
        Set<ConstraintViolation<T>> constraintViolationSet = validator.validate(t);
        if (!Check.NuNCollection(constraintViolationSet)) {
            dto = new DataTransferObject();
            Iterator<ConstraintViolation<T>> iterator = constraintViolationSet.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<T> constraint = iterator.next();
                dto.setErrCode(1);
                dto.putValue(constraint.getPropertyPath().toString(), constraint.getMessage());
                errorMsg.append(constraint.getMessage()).append(",");
            }
            //设置错误信息
            if (errorMsg.length() != 0) {
                errorMsg.deleteCharAt(errorMsg.length() - 1);
                dto.setMsg(errorMsg.toString());
            }
            return dto;
        }
        return dto;
    }
}
