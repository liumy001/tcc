/*
 * Copyright (c) 2016. ziroom.com.
 */
package com.eric.demo.commons.validator;

/**
 * <p></p>
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
public class ValidateResult<T> {
    /**
     * 返回数据
     */
    private T t;
    /**
     * 错误的话，返回的错误DTO
     */
    private DataTransferObject dto;
    /**
     * 是否成功
     */
    private boolean isSuccess;

    public static final boolean SUCCESS = true;
    public static final boolean FAULT = false;

    public ValidateResult(T t) {
        this.isSuccess = SUCCESS;
        this.t = t;
    }

    public ValidateResult(DataTransferObject dto) {
        this.isSuccess = FAULT;
        this.dto = dto;
    }

    public T getResultObj() {
        return t;
    }

    public DataTransferObject getDto() {
        return dto;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
