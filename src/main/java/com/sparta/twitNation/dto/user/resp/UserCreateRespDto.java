package com.sparta.twitNation.dto.user.resp;

import com.sparta.twitNation.domain.user.User;

public record UserCreateRespDto(Long id) {
    public UserCreateRespDto(User user) {
        this(user.getId());
    }
}
