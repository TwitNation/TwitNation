package com.sparta.twitNation.dto.follow.resp;

import com.sparta.twitNation.dto.bookmark.req.BookmarkPostDto;
import com.sparta.twitNation.dto.follow.req.FollowerDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor
public class FollowerViewRespDto {
    private List<FollowerDto> followers;
    private int elementsCount; // 전체 요소 수
    private int currentPage; // 현재 페이지
    private int nextPage; // 다음 페이지 번호
    private boolean nextPageBool; // 다음 페이지 존재 여부
    private int pageSize; // 페이지 크기

    public FollowerViewRespDto(List<FollowerDto> followers, int elementsCount, int currentPage, int nextPage, boolean nextPageBool, int pageSize) {
        this.followers = followers;
        this.elementsCount = elementsCount;
        this.currentPage = currentPage;
        this.nextPage = nextPage;
        this.nextPageBool = nextPageBool;
        this.pageSize = pageSize;
    }
}
