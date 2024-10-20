package com.sparta.twitNation.config.auth.dto;

import com.sparta.twitNation.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoginRespDto {

    private Long id;
    private String username;

    public LoginRespDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
