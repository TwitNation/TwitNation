package com.sparta.twitNation.domain.post.dto;

import java.time.LocalDateTime;

public interface PostDetailWithUser {
    Long getPostId();
    Long getUserId();
    String getNickname();
    String getContent();
    LocalDateTime getModifiedAt();
    String getProfileImg();
}
