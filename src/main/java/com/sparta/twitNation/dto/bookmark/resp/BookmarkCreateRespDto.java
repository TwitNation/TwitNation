package com.sparta.twitNation.dto.bookmark.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BookmarkCreateRespDto {

    @Schema(description = "북마크한 게시글의 아이디를 보여줍니다.")
    private Long postId;
    @Schema(description = "북마크에 저장되었는지를 알려줍니다.")
    private boolean isBookmarked;


    public BookmarkCreateRespDto(Long postId, boolean isBookmarked) {
        this.postId = postId;
        this.isBookmarked = isBookmarked;
    }
}
