package com.sparta.twitNation.handler;

import com.sparta.twitNation.ex.CustomApiException;
import com.sparta.twitNation.util.api.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResult<Map<String, String>>> validationException(MethodArgumentNotValidException e){
        Map<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(ApiResult.error(HttpStatus.BAD_REQUEST.value(), "유효성 검사 실패", errorMap), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<ApiResult<String>> apiException(CustomApiException e){
        return new ResponseEntity<>(ApiResult.error(e.getErrorCode().getStatus(), e.getErrorCode().getMessage()), HttpStatusCode.valueOf(e.getErrorCode().getStatus()));
    }
}
