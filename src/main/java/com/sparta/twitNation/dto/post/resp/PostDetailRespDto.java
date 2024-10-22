package com.sparta.twitNation.dto.post.resp;

import com.sparta.twitNation.domain.post.dto.PageDetailWithUser;

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
    public PostDetailRespDto(PageDetailWithUser pageDetailWithUser, int likeCount, int commentCount, int retweetCount) {
        this(
                pageDetailWithUser.postId(),
                pageDetailWithUser.userId(),
                pageDetailWithUser.nickname(),
                pageDetailWithUser.content(),
                pageDetailWithUser.modifiedAt(),
                pageDetailWithUser.profileImg(),
                likeCount,
                commentCount,
                retweetCount
        );
    }
}
