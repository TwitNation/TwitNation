package com.sparta.twitNation.dto.comment.resp;

import com.sparta.twitNation.domain.comment.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public record CommentListRespDto(
        @Schema(description = "검색된 댓글의 개수를 출력합니다.")
        long elementsCount,
        @Schema(description = "검색된 댓글 리스트중 현재 페이지를 출력합니다.")
        long currentPage,
        @Schema(description = "다음 페이지 번호를 출력합니다.")
        long nextPage,
        @Schema(description = "다음 페이지 존재 여부를 출력합니다.")
        boolean hasNextPage,
        @Schema(description = "리스트에 나타낼 갯수를 출력합니다.")
        long pageSize,
        @Schema(description = "검색된 댓글 리스트를 출력합니다..")
        List<CommentRespDto> commentList
) {
    public CommentListRespDto(Page<Comment> commentPage) {
        this(
                commentPage.getTotalElements(),
                commentPage.getNumber(),
                commentPage.hasNext() ? commentPage.getNumber() + 1 : -1,
                commentPage.hasNext(),
                commentPage.getSize(),
                commentPage.stream()
                        .map(CommentRespDto::new)
                        .toList()
        );
    }
}
