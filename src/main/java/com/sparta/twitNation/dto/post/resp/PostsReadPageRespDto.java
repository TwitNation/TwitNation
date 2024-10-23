package com.sparta.twitNation.dto.post.resp;

import com.sparta.twitNation.domain.post.dto.PostWithDetails;
import java.util.List;
import org.springframework.data.domain.Page;

public record PostsReadPageRespDto(
        int elementsCount,
        int currentPage,
        int nextPage,
        int pageCount,
        boolean nextPageBool,
        int pageSize,
        List<PostWithDetails> posts
) {
    public static PostsReadPageRespDto from(final Page<PostWithDetails> posts) {
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
