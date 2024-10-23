package com.sparta.twitNation.dto.comment.resp;

import com.sparta.twitNation.domain.comment.Comment;

import java.time.LocalDateTime;

public record CommentRespDto(
        Long commentId,
        String nickname,
        LocalDateTime lastModifiedAt,
        String profileImg,
        String content
) {
    public CommentRespDto(Comment comment) {
        this(
                comment.getId(),
                comment.getUser().getNickname(),
                comment.getLastModifiedAt(),
                comment.getUser().getProfileImg(),
                comment.getContent()
        );
    }
}
