package com.sparta.twitNation.controller;


import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.dto.post.req.PostCreateReqDto;
import com.sparta.twitNation.dto.post.req.PostModifyReqDto;
import com.sparta.twitNation.dto.post.resp.PostCreateRespDto;
import com.sparta.twitNation.dto.post.resp.PostDeleteRespDto;
import com.sparta.twitNation.dto.post.resp.PostModifyRespDto;
import com.sparta.twitNation.service.PostService;
import com.sparta.twitNation.util.api.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResult<PostCreateRespDto>> createPost(@RequestBody @Valid PostCreateReqDto postCreateReqDto, @AuthenticationPrincipal LoginUser loginUser){
        return new ResponseEntity<>(ApiResult.success(postService.createPost(postCreateReqDto, loginUser)), HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ApiResult<PostModifyRespDto>> modifyPost(@PathVariable(value = "postId") Long postId,
                                                                   @RequestBody @Valid PostModifyReqDto postModifyReqDto,
                                                                   @AuthenticationPrincipal LoginUser loginUser){
        return new ResponseEntity<>(ApiResult.success(postService.modifyPost(postModifyReqDto, postId, loginUser)), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResult<PostDeleteRespDto>> deletePost(@PathVariable(value = "postId")Long postId, @AuthenticationPrincipal LoginUser loginUser){
        return new ResponseEntity<>(ApiResult.success(postService.deletePost(postId, loginUser)), HttpStatus.OK);
    }
}
