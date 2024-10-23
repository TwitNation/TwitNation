package com.sparta.twitNation.controller;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.dto.like.resp.LikeCreateRespDto;
import com.sparta.twitNation.dto.like.resp.LikeReadPageListRespDto;
import com.sparta.twitNation.dto.like.resp.LikeReadPageRespDto;
import com.sparta.twitNation.service.LikeService;
import com.sparta.twitNation.util.api.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/api/users/likes")
    public ResponseEntity<ApiResult<LikeReadPageListRespDto>> likePage(
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        LikeReadPageListRespDto respDto = likeService.likePosts(loginUser.getUser().getId(), PageRequest.of(page, size));
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(respDto));
    }

}
