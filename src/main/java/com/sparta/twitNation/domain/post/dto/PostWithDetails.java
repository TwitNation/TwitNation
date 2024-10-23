package com.sparta.twitNation.domain.post.dto;

import java.time.LocalDateTime;

public interface PostWithDetails {

    String getUserNickname();

    String getUserProfileImg();

    String getContent();

    LocalDateTime getModifiedAt();

    int getLikeCount();

    int getCommentCount();

    int getRetweetCount();
}
