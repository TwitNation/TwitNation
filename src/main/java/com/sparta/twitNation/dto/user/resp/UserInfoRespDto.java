package com.sparta.twitNation.dto.user.resp;

import com.sparta.twitNation.domain.user.User;

import java.time.LocalDateTime;

public record UserInfoRespDto(String nickname, String bio, LocalDateTime createAt) {
    public UserInfoRespDto(User user) {
        this(user.getNickname(), user.getBio(), user.getCreatedAt());
    }
}
