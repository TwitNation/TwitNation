package com.sparta.twitNation.domain.like;

import com.sparta.twitNation.dto.like.resp.LikeReadPageRespDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikeCustomRepository {

    Page<LikeReadPageRespDto> searchLikes(Long userId, Pageable pageable);
}
