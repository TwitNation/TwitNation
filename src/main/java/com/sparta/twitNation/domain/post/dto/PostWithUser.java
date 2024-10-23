package com.sparta.twitNation.domain.post.dto;

import java.time.LocalDateTime;

public interface PostWithUser {

    Long getUserId();

    Long getPostId();

    String getUserNickname();

    String getUserProfileImg();

    String getContent();

    LocalDateTime getModifiedAt();

    int getLikeCount();

    int getCommentCount();

    int getRetweetCount();
}
