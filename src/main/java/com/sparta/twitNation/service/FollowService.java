package com.sparta.twitNation.service;

import com.sparta.twitNation.domain.follow.FollowingRepository;
import com.sparta.twitNation.dto.follow.resp.FollowingReadPageRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowingRepository followingRepository;

    public FollowingReadPageRespDto followingList(Long userId, Pageable pageable){
        return followingRepository.findAllFollowing(userId, pageable);
    }
}
