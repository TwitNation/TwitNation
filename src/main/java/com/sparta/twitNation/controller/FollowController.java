package com.sparta.twitNation.controller;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.dto.follow.resp.FollowCreateRespDto;
import com.sparta.twitNation.dto.follow.resp.FollowerViewRespDto;
import com.sparta.twitNation.dto.follow.resp.FollowingReadPageRespDto;
import com.sparta.twitNation.service.FollowService;
import com.sparta.twitNation.util.api.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FollowController implements FollowControllerDocs{

    private final FollowService followService;

    @GetMapping("/following/{userId}")
    public ResponseEntity<ApiResult<FollowingReadPageRespDto>> followings(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        FollowingReadPageRespDto respDto = followService.followingList(userId, PageRequest.of(page, size));
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(respDto));
    }

    @PostMapping("/follow/{userId}")
    public ResponseEntity<ApiResult<FollowCreateRespDto>> changeFollowState(
            @PathVariable(name = "userId") Long userId,
            @AuthenticationPrincipal LoginUser loginUser
    ) {
        FollowCreateRespDto response = followService.changeFollowState(loginUser, userId);
        return new ResponseEntity<>(ApiResult.success(response), HttpStatus.CREATED);
    }

    @GetMapping("/follows/{userId}")
    public ResponseEntity<ApiResult<FollowerViewRespDto>> getFollowers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @PathVariable Long userId
    ) {
        FollowerViewRespDto response = followService.getFollwers(page, limit, userId);
        return ResponseEntity.ok(ApiResult.success(response));
    }
}
