package com.sparta.twitNation.dto.comment.resp;

import com.sparta.twitNation.domain.comment.Comment;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public record CommentListRespDto(
        long elementsCount,
        long currentPage,
        Long nextPage,
        boolean hasNextPage,
        long pageSize,
        List<CommentRespDto> commentList
) {
    public CommentListRespDto(Page<Comment> commentPage) {
        this(
                commentPage.getTotalElements(),
                commentPage.getNumber(),
                commentPage.hasNext() ? (long) commentPage.getNumber() + 1 : null,
                commentPage.hasNext(),
                commentPage.getSize(),
                commentPage.stream()
                        .map(CommentRespDto::new)
                        .toList()
        );
    }
}
