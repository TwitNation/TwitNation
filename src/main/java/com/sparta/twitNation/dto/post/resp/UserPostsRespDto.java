package com.sparta.twitNation.dto.post.resp;

import com.sparta.twitNation.domain.post.dto.PostWithUser;
import java.util.List;
import org.springframework.data.domain.Page;

public record UserPostsRespDto(
        int elementsCount,
        int currentPage,
        int nextPage,
        int pageCount,
        boolean nextPageBool,
        int pageSize,
        List<PostWithUser> posts
) {
    public static UserPostsRespDto from(final Page<PostWithUser> posts) {
        return new UserPostsRespDto(
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
