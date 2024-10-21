package com.sparta.twitNation.dto.bookmark.resp;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BookmarkCreateRespDto {

    private Long postId;
    private boolean isBookmarked;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;


    public BookmarkCreateRespDto(Long postId, boolean isBookmarked, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.postId = postId;
        this.isBookmarked = isBookmarked;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }

    public BookmarkCreateRespDto(Long postId, boolean isBookmarked) {
        this.postId = postId;
        this.isBookmarked = isBookmarked;
    }
}
