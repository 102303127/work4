package com.zhang.advice.exception;

/**
 * 自定义异常
 *
 * @author 31445
 * &#064;
 * date 2024/01/25
 */
public class userException extends RuntimeException{

    private final String message;

    public userException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
