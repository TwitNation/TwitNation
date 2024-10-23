package com.sparta.twitNation.dto.post.resp;

import com.sparta.twitNation.domain.post.dto.PostDetailWithUser;

import java.time.LocalDateTime;

public record PostDetailRespDto(
        Long postId,
        Long userId,
        String nickname,
        String content,
        LocalDateTime modifiedAt,
        String profileImg,
        int likeCount,
        int commentCount,
        int retweetCount
) {
    public PostDetailRespDto(PostDetailWithUser postDetailWithUser, int likeCount, int commentCount, int retweetCount) {
        this(
                postDetailWithUser.getPostId(),
                postDetailWithUser.getUserId(),
                postDetailWithUser.getNickname(),
                postDetailWithUser.getContent(),
                postDetailWithUser.getModifiedAt(),
                postDetailWithUser.getProfileImg(),
                likeCount,
                commentCount,
                retweetCount
        );
    }
}
