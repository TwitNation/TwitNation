package com.sparta.twitNation.service;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.domain.follow.Follow;
import com.sparta.twitNation.domain.follow.FollowRepository;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.domain.user.UserRepository;
import com.sparta.twitNation.dto.follow.resp.FollowCreateRespDto;
import com.sparta.twitNation.ex.CustomApiException;
import com.sparta.twitNation.ex.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public FollowCreateRespDto changeFollowState(LoginUser loginUser, Long userId) {

        Long currentUserId = loginUser.getUser().getId();

        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.USER_NOT_FOUND));

        Optional<Follow> existingFollow = followRepository.findById(userId);

        if (existingFollow.isPresent()) {
            followRepository.delete(existingFollow.get());

        } else {
            Follow newFollow = new Follow(loginUser.getUser().getId(), targetUser);
            followRepository.save(newFollow);

        }

        return new FollowCreateRespDto(userId, existingFollow.isEmpty());


    }
}
