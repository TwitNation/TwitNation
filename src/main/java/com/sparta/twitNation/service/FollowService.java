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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public FollowCreateRespDto changeFollowState(LoginUser loginUser, Long targetUserId) {

        User currentUser = userRepository.findById(loginUser.getUser().getId())
                .orElseThrow(() -> new CustomApiException(ErrorCode.USER_NOT_FOUND));


        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.USER_NOT_FOUND));

        if (currentUser.getId().equals(targetUserId)) {
            throw new CustomApiException(ErrorCode.FOLLOW_FORBIDDEN);
        }


        Optional<Follow> existingFollow = followRepository.findByFollowerAndFollowing(currentUser, targetUser);

        if (existingFollow.isPresent()) {
            followRepository.delete(existingFollow.get());
            return new FollowCreateRespDto(targetUserId, false);  // 언팔로우 상태


        } else {
            Follow newFollow = Follow.builder()
                    .follower(currentUser)
                    .following(targetUser)
                    .build();
            followRepository.save(newFollow);
            return new FollowCreateRespDto(targetUserId, true);  // 팔로우 상태

        }


    }

}
