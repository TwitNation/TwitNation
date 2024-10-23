package com.sparta.twitNation.controller;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.dto.post.req.PostCreateReqDto;
import com.sparta.twitNation.dto.post.req.PostModifyReqDto;
import com.sparta.twitNation.dto.post.resp.PostCreateRespDto;
import com.sparta.twitNation.dto.post.resp.PostDeleteRespDto;
import com.sparta.twitNation.dto.post.resp.PostModifyRespDto;
import com.sparta.twitNation.dto.post.resp.PostsSearchPageRespDto;
import com.sparta.twitNation.dto.post.resp.UserPostsRespDto;
import com.sparta.twitNation.service.PostService;
import com.sparta.twitNation.util.api.ApiResult;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return new ResponseEntity<>(ApiResult.success(postService.createPost(postCreateReqDto, loginUser)),
                HttpStatus.CREATED);
    }

    // 게시물 수정
    @PutMapping("/{postId}")
    public ResponseEntity<ApiResult<PostModifyRespDto>> modifyPost(
            @PathVariable(value = "postId") Long postId,
            @RequestBody @Valid PostModifyReqDto postModifyReqDto,
            @AuthenticationPrincipal LoginUser loginUser
    ) {
        return new ResponseEntity<>(ApiResult.success(postService.modifyPost(postModifyReqDto, postId, loginUser)),
                HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResult<PostDeleteRespDto>> deletePost(@PathVariable(value = "postId")Long postId, @AuthenticationPrincipal LoginUser loginUser){
        return new ResponseEntity<>(ApiResult.success(postService.deletePost(postId, loginUser)), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResult<UserPostsRespDto>> readPostByUser(
            @PathVariable final Long userId,
            @RequestParam(defaultValue = "0", value = "page") final int page,
            @RequestParam(defaultValue = "10", value = "limit") final int limit
    ) {
        final UserPostsRespDto response = postService.readPostsBy(userId, page, limit);
        return new ResponseEntity<>(ApiResult.success(response), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResult<PostsSearchPageRespDto>> searchPosts(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int limit,
            @RequestParam(required = false) final String keyword,
            @RequestParam(required = false) final LocalDateTime startModifiedAt,
            @RequestParam(required = false) final LocalDateTime endModifiedAt,
            @RequestParam(defaultValue = "lastModifiedAt, desc") final String sort
    ) {
        final PostsSearchPageRespDto response = postService.searchKeyword(sort, keyword, page, limit, startModifiedAt,
                endModifiedAt);
        return new ResponseEntity<>(ApiResult.success(response), HttpStatus.OK);
    }
}

