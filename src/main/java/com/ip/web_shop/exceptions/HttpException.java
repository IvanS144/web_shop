package com.ip.web_shop.exceptions;

import com.ip.web_shop.exceptions.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpException extends RuntimeException{
    private int statusCode;
    private ErrorMessage errorMessage;

    public HttpException(int statusCode, String message){
        this.statusCode = statusCode;
        this.errorMessage = new ErrorMessage(message);
    }
}
