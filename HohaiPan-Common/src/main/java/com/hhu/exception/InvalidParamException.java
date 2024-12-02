package com.hhu.exception;

/*
参数错误异常
 */
public class InvalidParamException extends BaseException {

    public InvalidParamException() {
    }

    public InvalidParamException(String msg) {
        super(msg);
    }

}
