package com.sparta.twitNation.controller;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.dto.comment.req.CommentCreateReqDto;
import com.sparta.twitNation.dto.comment.req.CommentModifyReqDto;
import com.sparta.twitNation.dto.comment.resp.CommentCreateRespDto;
import com.sparta.twitNation.dto.comment.resp.CommentModifyRespDto;
import com.sparta.twitNation.service.CommentService;
import com.sparta.twitNation.util.api.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Validated
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiResult<CommentCreateRespDto>> createComment(@PathVariable(name = "postId") Long postId,
                                                                         @RequestBody @Valid CommentCreateReqDto commentCreateReqDto,
                                                                         @AuthenticationPrincipal LoginUser loginUser) {
        return new ResponseEntity<>(ApiResult.success(commentService.createComment(commentCreateReqDto,loginUser,postId)), HttpStatus.CREATED);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResult<CommentModifyRespDto>> updateComment(@PathVariable(name = "postId") Long postId,
                                                                         @PathVariable(name = "commentId") Long commentId,
                                                                         @RequestBody @Valid CommentModifyReqDto commentModifyReqDto,
                                                                         @AuthenticationPrincipal LoginUser loginUser) {
        return new ResponseEntity<>(ApiResult.success(commentService.updateComment(postId, commentId, commentModifyReqDto, loginUser)), HttpStatus.OK);
    }

}
