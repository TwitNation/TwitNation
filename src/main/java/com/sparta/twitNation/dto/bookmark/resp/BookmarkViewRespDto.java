package com.sparta.twitNation.dto.bookmark.resp;

import com.sparta.twitNation.dto.bookmark.req.BookmarkPostDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BookmarkViewRespDto {

    @Schema(description = "북마크 한 게시글의 목록을 가져옵니다.")
    private List<BookmarkPostDto> posts; // 게시글 목록
    @Schema(description = "북마크 한 게시글의 수를 나타냅니다.")
    private int elementsCount; // 전체 요소 수
    @Schema(description = "현제 페이지를 나타냅니다.")
    private int currentPage; // 현재 페이지
    @Schema(description = "다음 페이지를 나타냅니다.")
    private int nextPage; // 다음 페이지 번호
    @Schema(description = "다음 페이지의 존재 여부를 나타냅니다.")
    private boolean nextPageBool; // 다음 페이지 존재 여부
    @Schema(description = "페이지의 크기를 나타냅니다.")
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
