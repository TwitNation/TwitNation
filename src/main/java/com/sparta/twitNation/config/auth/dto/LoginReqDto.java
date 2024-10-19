package com.sparta.twitNation.config.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoginReqDto {

    private String username;
    private String password;
}
