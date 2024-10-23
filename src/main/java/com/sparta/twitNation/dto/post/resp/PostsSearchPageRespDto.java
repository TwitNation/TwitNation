package com.sparta.twitNation.dto.post.resp;

import java.util.List;
import org.springframework.data.domain.Page;

public record PostsSearchPageRespDto(
        int elementsCount,
        int currentPage,
        int nextPage,
        int pageCount,
        boolean nextPageBool,
        int pageSize,
        List<PostsSearchRespDto> posts
) {
    public static PostsSearchPageRespDto from(final Page<PostsSearchRespDto> posts) {
        return new PostsSearchPageRespDto(
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
