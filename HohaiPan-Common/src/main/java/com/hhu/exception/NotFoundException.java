package com.hhu.exception;

/**
 * 账号不存在异常
 */
public class NotFoundException extends BaseException {

    public NotFoundException() {
    }

    public NotFoundException(String msg) {
        super(msg);
    }

}
