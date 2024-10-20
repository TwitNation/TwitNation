package com.sparta.twitNation.config.auth.dto;

import com.sparta.twitNation.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record LoginRespDto(Long id, String username) {
    public LoginRespDto(User user) {
        this(user.getId(), user.getUsername());
    }
}
