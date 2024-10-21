package com.sparta.twitNation.ex;

import lombok.Getter;

@Getter
public class CustomApiException extends RuntimeException{

    private final int status;
    private final String msg;
    public CustomApiException(String message, int  status){
        super(message);
        this.msg = message;
        this.status = status;
    }

}
