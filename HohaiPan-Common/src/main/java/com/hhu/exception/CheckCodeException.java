package com.hhu.exception;

/**
 * 验证码异常
 */
public class CheckCodeException extends BaseException{
    public CheckCodeException() {

    }
    public CheckCodeException(String message) {
        super(message);
    }
}
