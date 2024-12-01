package com.hhu.exception;

/**
 * 用户已存在异常
 */
public class AlreadyExistsException extends BaseException {

    public AlreadyExistsException() {
    }

    public AlreadyExistsException(String msg) {
        super(msg);
    }
}
