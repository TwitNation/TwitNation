package com.sparta.twitNation.controller;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.dto.post.req.PostCreateReqDto;
import com.sparta.twitNation.dto.post.req.PostModifyReqDto;
import com.sparta.twitNation.dto.post.resp.*;
import com.sparta.twitNation.service.PostService;
import com.sparta.twitNation.util.api.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    // 게시물 생성
    @PostMapping
    public ResponseEntity<ApiResult<PostCreateRespDto>> createPost(
            @RequestBody @Valid PostCreateReqDto postCreateReqDto,
            @AuthenticationPrincipal LoginUser loginUser
    ) {
        return new ResponseEntity<>(ApiResult.success(postService.createPost(postCreateReqDto, loginUser)), HttpStatus.CREATED);
    }

    // 게시물 수정
    @PutMapping("/{postId}")
    public ResponseEntity<ApiResult<PostModifyRespDto>> modifyPost(
            @PathVariable(value = "postId") Long postId,
            @RequestBody @Valid PostModifyReqDto postModifyReqDto,
            @AuthenticationPrincipal LoginUser loginUser
    ) {
        return new ResponseEntity<>(ApiResult.success(postService.modifyPost(postModifyReqDto, postId, loginUser)), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResult<PostDeleteRespDto>> deletePost(@PathVariable(value = "postId")Long postId, @AuthenticationPrincipal LoginUser loginUser){
        return new ResponseEntity<>(ApiResult.success(postService.deletePost(postId, loginUser)), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResult<UserPostsRespDto>> readPostByUser(
            @PathVariable(value = "userId") final Long userId,
            @RequestParam(defaultValue = "0", value = "page") int page,
            @RequestParam(defaultValue = "10", value = "limit") int limit
    ) {
        final UserPostsRespDto response = postService.readPostsBy(userId, page, limit);
        return new ResponseEntity<>(ApiResult.success(response), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResult<PostDetailRespDto>> getPostById(@PathVariable(value = "postId")Long postId, @AuthenticationPrincipal LoginUser loginUser){
        return new ResponseEntity<>(ApiResult.success(postService.getPostById(postId, loginUser)),HttpStatus.OK);
    }
}

