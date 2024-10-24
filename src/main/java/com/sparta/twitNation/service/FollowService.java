package com.sparta.twitNation.service;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.domain.follow.Follow;
import com.sparta.twitNation.domain.follow.FollowRepository;
import com.sparta.twitNation.domain.follow.FollowingRepository;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.domain.user.UserRepository;
import com.sparta.twitNation.dto.follow.req.FollowerDto;
import com.sparta.twitNation.dto.follow.resp.FollowCreateRespDto;
import com.sparta.twitNation.dto.follow.resp.FollowerViewRespDto;
import com.sparta.twitNation.dto.follow.resp.FollowingReadPageRespDto;
import com.sparta.twitNation.ex.CustomApiException;
import com.sparta.twitNation.ex.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowingRepository followingRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public FollowingReadPageRespDto followingList(Long userId, Pageable pageable) {
        return followingRepository.findAllFollowing(userId, pageable);
    }

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

    public FollowerViewRespDto getFollwers(int page, int limit, Long userId) {

//        Long userId = loginUser.getUser().getId();
        //PageRequest pageRequest = PageRequest.of(page, limit);
        // 정렬 조건 추가 (아이디 기준으로 오름차순 정렬)
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "lastModifiedAt"));


        Page<User> followers = followRepository.findFollowersByUserId(userId, pageRequest);

        // 팔로워 정보를 DTO로 변환
        List<FollowerDto> followerDtos = followers.getContent().stream()
                .map(follower -> new FollowerDto(
                        follower.getId(),
                        follower.getNickname(),
                        follower.getProfileImg()
                ))
                .collect(Collectors.toList());


        return new FollowerViewRespDto(
                followerDtos,
                (int) followers.getTotalElements(),
                followers.getNumber(),
                followers.hasNext() ? followers.getNumber() + 1 : -1,
                followers.hasNext(),
                followers.getSize()
        );
    }
}
