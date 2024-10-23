package com.sparta.twitNation.dto.comment.resp;

import io.swagger.v3.oas.annotations.media.Schema;

public record CommentDeleteRespDto(
        @Schema(description = "삭제된 댓글의 Id를 출력합니다.", required = true)
        Long commentId,
        @Schema(description = "댓글이 정상적으로 삭제되었는지 상태값을 출력합니다.", required = true)
        boolean isDeleted) {
}
