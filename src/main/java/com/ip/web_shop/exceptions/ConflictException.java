package com.ip.web_shop.exceptions;

public class ConflictException extends HttpException{
    public ConflictException(String message){
        super(409, message);
    }
}
