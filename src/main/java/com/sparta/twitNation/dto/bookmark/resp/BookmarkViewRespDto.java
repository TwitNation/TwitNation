package com.sparta.twitNation.dto.bookmark.resp;

import com.sparta.twitNation.dto.bookmark.req.BookmarkPostDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BookmarkViewRespDto {
    @Schema(description = "북마크 게시물 조회 결과입니다.")

    private List<BookmarkPostDto> posts; // 게시글 목록
    private int elementsCount; // 전체 요소 수
    private int currentPage; // 현재 페이지
    private int nextPage; // 다음 페이지 번호
    private boolean nextPageBool; // 다음 페이지 존재 여부
    private int pageSize; // 페이지 크기

    public BookmarkViewRespDto(List<BookmarkPostDto> posts, int elementsCount, int currentPage, int nextPage, boolean nextPageBool, int pageSize) {
        this.posts = posts;
        this.elementsCount = elementsCount;
        this.currentPage = currentPage;
        this.nextPage = nextPage;
        this.nextPageBool = nextPageBool;
        this.pageSize = pageSize;
    }
}
