package com.sparta.twitNation.dto.follow.resp;

import com.sparta.twitNation.dto.like.resp.LikeReadPageRespDto;
import org.springframework.data.domain.Page;

import java.util.List;

public record FollowingReadPageRespDto (
        Long elementsCount,
        int currentPage,
        int nextPage,
        boolean hasNextPage,
        int pageSize,
        List<FollowingReadRespDto> respDtos
){
    public FollowingReadPageRespDto(Page<FollowingReadRespDto> dtos){
        this(
                dtos.getTotalElements(),
                dtos.getNumber(),
                dtos.hasNext() ? dtos.getNumber() + 1 : -1,
                dtos.hasNext(),
                dtos.getSize(),
                dtos.stream()
                        .map(e -> new FollowingReadRespDto(e.getUserId(), e.getNickname(), e.getProfileImg()))
                        .toList()
        );
    }
}
