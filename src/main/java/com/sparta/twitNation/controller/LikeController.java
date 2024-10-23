package com.sparta.twitNation.controller;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.dto.like.resp.LikeCreateRespDto;
import com.sparta.twitNation.service.LikeService;
import com.sparta.twitNation.util.api.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/api/posts/{postId}/likes")
    public ResponseEntity<ApiResult<LikeCreateRespDto>> likeOrUnlike(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable Long postId
    ) {
        LikeCreateRespDto respDto = likeService.toggleLike(loginUser.getUser(), postId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.success(respDto));
    }

}
