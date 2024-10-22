package com.sparta.twitNation.dto.post.resp;

import java.util.List;
import org.springframework.data.domain.Page;

public record PostsReadPageRespDto(
        int elementsCount,
        int currentPage,
        int nextPage,
        int pageCount,
        boolean nextPageBool,
        int pageSize,
        List<PostsReadRespDto> posts
) {
    public static PostsReadPageRespDto of(final Page<PostsReadRespDto> posts) {
        return new PostsReadPageRespDto(
                (int) posts.getTotalElements(),
                posts.getNumber(),
                posts.hasNext() ? posts.getNumber() + 1 : -1,
                posts.getTotalPages(),
                posts.hasNext(),
                posts.getSize(),
                posts.getContent()
        );
    }
}
