package com.sparta.twitNation.dto.bookmark.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BookmarkCreateRespDto {
    @Schema(description = "북마크 추가/삭제가 완료되었습니다")

    private Long postId;
    private boolean isBookmarked;


    public BookmarkCreateRespDto(Long postId, boolean isBookmarked) {
        this.postId = postId;
        this.isBookmarked = isBookmarked;
    }
}
