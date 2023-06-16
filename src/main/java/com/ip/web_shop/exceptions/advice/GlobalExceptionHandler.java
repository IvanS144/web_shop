package com.ip.web_shop.exceptions.advice;

import com.ip.web_shop.exceptions.ErrorMessage;
import com.ip.web_shop.exceptions.HttpException;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@CommonsLog
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ErrorMessage> handleHttpException(HttpException e){
        return ResponseEntity.status(e.getStatusCode()).body(e.getErrorMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e){
        log.error("An error occurred", e);
        return ResponseEntity.status(500).body(new ErrorMessage("An error occurred"));
    }

}
