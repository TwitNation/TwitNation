package com.sparta.twitNation.controller;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.dto.bookmark.resp.BookmarkCreateRespDto;
import com.sparta.twitNation.dto.bookmark.resp.BookmarkViewRespDto;
import com.sparta.twitNation.dto.follow.resp.FollowCreateRespDto;
import com.sparta.twitNation.dto.follow.resp.FollowerViewRespDto;
import com.sparta.twitNation.service.FollowService;
import com.sparta.twitNation.util.api.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow/{userId}")
    public ResponseEntity<ApiResult<FollowCreateRespDto>> changeFollowState(@PathVariable(name = "userId") Long userId, @AuthenticationPrincipal LoginUser loginUser) {

        FollowCreateRespDto response = followService.changeFollowState(loginUser, userId);

        return new ResponseEntity<>(ApiResult.success(response), HttpStatus.CREATED);

    }

        @GetMapping("/follow/{userId}")
    public ResponseEntity<ApiResult<FollowerViewRespDto>> getFollowers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @AuthenticationPrincipal LoginUser loginUser
    ){
        FollowerViewRespDto response = followService.getFollwers(page, limit, loginUser);
        return ResponseEntity.ok(ApiResult.success(response));

    }

}