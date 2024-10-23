package com.sparta.twitNation.dto.bookmark.resp;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BookmarkCreateRespDto {

    private Long postId;
    private boolean isBookmarked;


    public BookmarkCreateRespDto(Long postId, boolean isBookmarked) {
        this.postId = postId;
        this.isBookmarked = isBookmarked;
    }
}
