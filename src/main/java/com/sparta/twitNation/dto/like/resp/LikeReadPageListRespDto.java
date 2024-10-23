package com.sparta.twitNation.dto.like.resp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public record LikeReadPageListRespDto(
        Long elementsCount,
        int currentPage,
        int nextPage,
        boolean hasNextPage,
        long pageSize,
        List<LikeReadPageRespDto> respDtos
) {
    public LikeReadPageListRespDto(Page<LikeReadPageRespDto> likePage) {
        this(
                likePage.getTotalElements(),
                likePage.getNumber(),
                likePage.hasNext() ? likePage.getNumber() + 1 : -1,
                likePage.hasNext(),
                likePage.getSize(),
                likePage.stream()
                        .map(e -> {
                            return new LikeReadPageRespDto(
                                    e.postId(),
                                    e.userId(),
                                    e.userNickname(),
                                    e.userProfileImg(),
                                    e.content(),
                                    e.modifiedAt(),
                                    e.likeCount(),
                                    e.commentCount(),
                                    e.retweetCount()
                            );
                        }).toList()
        );
    }
}
