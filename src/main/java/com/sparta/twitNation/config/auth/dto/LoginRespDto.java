package com.sparta.twitNation.config.auth.dto;

import com.sparta.twitNation.domain.user.User;

public record LoginRespDto(Long id) {
    public LoginRespDto(User user) {
        this(user.getId());
    }
}
