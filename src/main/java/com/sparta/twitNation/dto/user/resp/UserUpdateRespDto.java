package com.sparta.twitNation.dto.user.resp;

import com.sparta.twitNation.domain.user.User;

public record UserUpdateRespDto(
        String nickname,
        String email,
        String bio,
        String password
) {
    public UserUpdateRespDto(User user) {
        this(user.getNickname(), user.getEmail(), user.getBio(), user.getPassword());
    }
}
