package com.sparta.twitNation.dto.user.resp;

import com.sparta.twitNation.domain.user.User;

public record UserCreateRespDto(Long id, String emai) {
    public UserCreateRespDto(User user) {
        this(user.getId(), user.getEmail());
    }
}
