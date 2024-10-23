package com.sparta.twitNation.controller;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.dto.retweet.resp.RetweetToggleRespDto;
import com.sparta.twitNation.service.RetweetService;
import com.sparta.twitNation.util.api.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class RetweetController {

    private final RetweetService retweetService;

    @PostMapping("/{postId}/retweet")
    public ResponseEntity<ApiResult<RetweetToggleRespDto>> toggleRetweet(@PathVariable Long postId, @AuthenticationPrincipal LoginUser loginUser) {
        return new ResponseEntity<>(ApiResult.success(retweetService.toggleRetweet(postId,loginUser)), HttpStatus.OK);
    }

}
