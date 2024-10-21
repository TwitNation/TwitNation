package com.sparta.twitNation.dto.comment.resp;

import java.time.LocalDateTime;

public record CommentModifyRespDto(Long postId, Long commentId, LocalDateTime lastModifiedAt) {
}
