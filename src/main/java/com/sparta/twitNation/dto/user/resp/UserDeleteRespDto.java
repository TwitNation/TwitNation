package com.sparta.twitNation.dto.user.resp;

public record UserDeleteRespDto(Long userId) {
    public UserDeleteRespDto(Long userId) {
        this.userId = userId;
    }
}
