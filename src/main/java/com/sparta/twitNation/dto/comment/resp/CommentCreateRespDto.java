package com.sparta.twitNation.dto.comment.resp;

import io.swagger.v3.oas.annotations.media.Schema;

public record CommentCreateRespDto(
        @Schema(description = "댓글이 작성된 게시글의 Id를 출력합니다.")
        Long postId,
        @Schema(description = "작성된 댓글의 Id를 출력합니다.")
        Long commentId) { }
