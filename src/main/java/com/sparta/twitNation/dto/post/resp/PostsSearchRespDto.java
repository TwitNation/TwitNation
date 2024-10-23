package com.sparta.twitNation.dto.post.resp;

import java.time.LocalDateTime;

public record PostsSearchRespDto(
        Long postId,
        Long userId,
        String userNickname,
        String userProfileImg,
        String content,
        LocalDateTime modifiedAt,
        Long likeCount,
        Long commentCount,
        Long retweetCount
) {
    public PostsSearchRespDto(Long postId, Long userId, String userNickname, String userProfileImg, String content,
                              LocalDateTime modifiedAt, Long likeCount, Long commentCount, Long retweetCount) {
        this.postId = postId;
        this.userId = userId;
        this.userNickname = userNickname;
        this.userProfileImg = userProfileImg;
        this.content = content;
        this.modifiedAt = modifiedAt;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.retweetCount = retweetCount;
    }
}


