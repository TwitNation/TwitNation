package com.sparta.twitNation.dto.retweet.resp;

import io.swagger.v3.oas.annotations.media.Schema;

public record RetweetToggleRespDto(
        @Schema(description = "리트윗한 게시글의 Id를 보여줍니다.")
        Long postId,
        @Schema(description = "현재 게시글의 리트윗 상태를 보여줍니다.")
        boolean isRetweeted) {
}
