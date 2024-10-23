package com.sparta.twitNation.controller;

import com.sparta.twitNation.dto.follow.resp.FollowingReadPageRespDto;
import com.sparta.twitNation.service.FollowService;
import com.sparta.twitNation.util.api.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @GetMapping("/api/following/{userId}")
    public ResponseEntity<ApiResult<FollowingReadPageRespDto>> followings(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        FollowingReadPageRespDto respDto = followService.followingList(userId, PageRequest.of(page, size));
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(respDto));
    }
}
