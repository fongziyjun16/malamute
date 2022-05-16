package com.cyj.rbacalpha.exception;

public class AuthenticationFailureException extends RuntimeException{
    public AuthenticationFailureException(String message) {
        super(message);
    }
}
