package com.example.project3.exception;

public class AuthenticateException extends RuntimeException{
    public AuthenticateException(String mess){
        super(mess);
    }
}
