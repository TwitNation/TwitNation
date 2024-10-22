package com.sparta.twitNation.controller;

import com.sparta.twitNation.dto.post.resp.UserPostsRespDto;
import com.sparta.twitNation.service.PostService;
import com.sparta.twitNation.util.api.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/api/posts/{userId}")
    public ResponseEntity<ApiResult<UserPostsRespDto>> readPostByUser(
            @PathVariable final Long userId,
            @RequestParam(defaultValue = "0", value = "page") int page,
            @RequestParam(defaultValue = "10", value = "limit") int limit
    ) {
        final UserPostsRespDto response = postService.readPostsBy(userId, page, limit);
        return new ResponseEntity<>(ApiResult.success(response), HttpStatus.OK);
    }
}
