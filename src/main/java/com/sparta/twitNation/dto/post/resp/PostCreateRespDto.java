package com.sparta.twitNation.dto.post.resp;

import com.sparta.twitNation.domain.post.Post;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시글 생성 요청 DTO")
public record PostCreateRespDto(Long postId) {

    public PostCreateRespDto(Post post) {
        this(post.getId());
    }
}
