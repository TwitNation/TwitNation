package com.sparta.twitNation.domain.follow;

import com.sparta.twitNation.dto.follow.resp.FollowingReadPageRespDto;
import org.springframework.data.domain.Pageable;

public interface FollowingRepository {

    FollowingReadPageRespDto findAllFollowing(Long userId, Pageable pageable);
}
