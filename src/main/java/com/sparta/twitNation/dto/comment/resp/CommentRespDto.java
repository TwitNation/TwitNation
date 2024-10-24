package com.sparta.twitNation.dto.comment.resp;

import com.sparta.twitNation.domain.comment.Comment;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record CommentRespDto(
        @Schema(description = "댓글의 Id를 출력합니다.")
        Long commentId,
        @Schema(description = "댓글 작성자의 nickname을 출력합니다.")
        String nickname,
        @Schema(description = "댓글의 마지막 수정 날짜를 출력합니다.")
        LocalDateTime lastModifiedAt,
        @Schema(description = "댓글 작성자의 profileImg를 출력합니다.")
        String profileImg,
        @Schema(description = "댓글의 내용을 출력합니다.")
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
