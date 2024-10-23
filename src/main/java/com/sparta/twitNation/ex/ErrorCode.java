package com.sparta.twitNation.ex;

import com.sparta.twitNation.service.UserService;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(404, "존재하지 않는 유저입니다"),
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류"),
    POST_FORBIDDEN(403, "해당 게시글을 수정할 권한이 없습니다"),
    POST_NOT_FOUND(404, "존재하지 않는 게시글입니다"),
    COMMENT_FORBIDEN(403, "해당 댓글을 수정할 권한이 없습니다."),
    COMMENT_NOT_FOUND(404, "존재하지 않는 댓글입니다."),
    ALREADY_USER_EXIST(400, "존재하는 사용자입니다."),
    TOKEN_MISSING(401, "Authorization 헤더가 누락되었습니다"),
    MISS_MATCHER_PASSWORD(401, "비밀번호가 일치하지 않습니다."),
    FILE_UPLOAD_ERROR(500, "파일 업로드 중 오류가 발생했습니다"),
    FILE_NOT_FOUND(400,"파일이 없습니다"),
    FILE_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE.value(), "파일 크기가 허용된 최대 크기를 초과했습니다.");



    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }


}
