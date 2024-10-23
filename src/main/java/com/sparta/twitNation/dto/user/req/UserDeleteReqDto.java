package com.sparta.twitNation.dto.user.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDeleteReqDto(
        @NotBlank
        @Size(min = 8, max = 20, message = "패스워드 글자 수는 20자까지만 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", // test : password1!
                message = "비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 최소 8글자 이상이어야 합니다.")
        String password
){

}
