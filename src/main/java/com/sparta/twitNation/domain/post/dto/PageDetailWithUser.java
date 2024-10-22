package com.sparta.twitNation.domain.post.dto;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

public record PageDetailWithUser(
        Long postId,
        Long userId,
        String nickname,
        String content,
        LocalDateTime modifiedAt,
        String profileImg) {
}
