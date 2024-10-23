package com.sparta.twitNation.controller;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.dto.comment.resp.CommentListRespDto;
import com.sparta.twitNation.dto.post.req.PostCreateReqDto;
import com.sparta.twitNation.dto.post.req.PostModifyReqDto;
import com.sparta.twitNation.dto.post.resp.PostCreateRespDto;
import com.sparta.twitNation.dto.post.resp.PostDeleteRespDto;
import com.sparta.twitNation.dto.post.resp.PostDetailRespDto;
import com.sparta.twitNation.dto.post.resp.PostModifyRespDto;
import com.sparta.twitNation.dto.post.resp.PostsReadPageRespDto;
import com.sparta.twitNation.dto.post.resp.PostsSearchPageRespDto;
import com.sparta.twitNation.dto.post.resp.UserPostsRespDto;
import com.sparta.twitNation.service.PostService;
import com.sparta.twitNation.util.api.ApiResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@Validated
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
    public ResponseEntity<ApiResult<PostDeleteRespDto>> deletePost(@PathVariable(value = "postId") Long postId,
                                                                   @AuthenticationPrincipal LoginUser loginUser) {
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

    @GetMapping
    public ResponseEntity<ApiResult<PostsReadPageRespDto>> readPosts(
            @RequestParam(defaultValue = "0", value = "page") int page,
            @RequestParam(defaultValue = "10", value = "limit") int limit
    ) {
        final PostsReadPageRespDto response = postService.readPosts(page, limit);
        return new ResponseEntity<>(ApiResult.success(response), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResult<PostDetailRespDto>> getPostById(@PathVariable(value = "postId") Long postId,
                                                                    @AuthenticationPrincipal LoginUser loginUser) {
        return new ResponseEntity<>(ApiResult.success(postService.getPostById(postId, loginUser)), HttpStatus.OK);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<ApiResult<CommentListRespDto>> getCommentsByPostId(
            @PathVariable(value = "postId") Long postId,
            @RequestParam(defaultValue = "0", value = "page") @Min(0) int page,
            @RequestParam(defaultValue = "10", value = "limit") @Positive int limit,
            @AuthenticationPrincipal LoginUser loginUser) {
        return new ResponseEntity<>(
                ApiResult.success(postService.getCommentsByPostId(postId, loginUser.getUser(), page, limit)),
                HttpStatus.OK);
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