package com.sparta.twitNation.dto.like.resp;

import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.user.User;

import java.time.LocalDateTime;

public record LikeReadPageRespDto(
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

    public static LikeReadPageRespDto from(
            Post post,
            User user,
            Long likeCount,
            Long commentCount,
            Long retweetCount
    ) {
        return new LikeReadPageRespDto(
                post.getId(),
                user.getId(),
                user.getNickname(),
                user.getProfileImg(),
                post.getContent(),
                post.getLastModifiedAt(),
                likeCount,
                commentCount,
                retweetCount
        );
    }
}
