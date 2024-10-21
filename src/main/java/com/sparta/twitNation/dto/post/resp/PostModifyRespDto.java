package com.sparta.twitNation.dto.post.resp;

import com.sparta.twitNation.domain.post.Post;

public record PostModifyRespDto(String content) {
    public PostModifyRespDto(Post post) {
        this(post.getContent());
    }
}
