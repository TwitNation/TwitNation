package com.sparta.twitNation.dto.post.resp;

import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.user.User;
import java.time.LocalDateTime;

public record PostReadPageRespDto(
        String userNickname,
        String userProfileImg,
        String content,
        LocalDateTime modifiedAt,
        int likeCount,
        int commentCount,
        int retweetCount
) {

    public static PostReadPageRespDto from(
            final User user,
            final Post post,
            final int likeCount,
            final int commentCount,
            final int retweetCount
    ) {
        return new PostReadPageRespDto(
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
