package com.sparta.twitNation.dto.post.resp;

import com.sparta.twitNation.domain.post.Post;

public record PostCreateRespDto(Long postId) {

    public PostCreateRespDto (Post post){
        this(post.getId());
    }
}
