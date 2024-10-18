package com.sparta.twitNation.ex;

public class CustomApiException extends RuntimeException{
    public CustomApiException(String message){
        super(message);
    }

}
