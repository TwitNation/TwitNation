package com.sparta.twitNation.ex;
import lombok.Getter;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(404, "존재하지 않는 유저입니다"),
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류");


    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }


}
