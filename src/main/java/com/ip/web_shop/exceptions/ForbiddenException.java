package com.ip.web_shop.exceptions;

public class ForbiddenException extends HttpException{
    public ForbiddenException(String message){
        super(403, message);
    }
}
