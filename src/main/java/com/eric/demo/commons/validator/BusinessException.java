package com.eric.demo.commons.validator;

public class BusinessException extends Exception {

    public BusinessException(String message,Exception e) {
        super(message,e);
    }
}
