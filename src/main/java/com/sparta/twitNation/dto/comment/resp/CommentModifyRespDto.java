package com.sparta.twitNation.dto.comment.resp;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record CommentModifyRespDto(
        @Schema(description = "수정된 댓글의 게시물 Id를 출력합니다.")
        Long postId,
        @Schema(description = "수정된 댓글의 Id를 출력합니다.")
        Long commentId,
        @Schema(description = "수정 시간을 출력합니다.")
        LocalDateTime lastModifiedAt) {
}
