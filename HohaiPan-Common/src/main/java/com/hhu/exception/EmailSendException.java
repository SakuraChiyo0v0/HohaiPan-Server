package com.hhu.exception;

/**
 * 右键发送异常
 */
public class EmailSendException extends BaseException {
    public EmailSendException() {
    }

    public EmailSendException(String msg) {
        super(msg);
    }
}
