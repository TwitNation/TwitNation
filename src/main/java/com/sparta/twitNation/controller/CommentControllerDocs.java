package com.sparta.twitNation.controller;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.dto.comment.req.CommentCreateReqDto;
import com.sparta.twitNation.dto.comment.req.CommentModifyReqDto;
import com.sparta.twitNation.dto.comment.resp.CommentCreateRespDto;
import com.sparta.twitNation.dto.comment.resp.CommentDeleteRespDto;
import com.sparta.twitNation.dto.comment.resp.CommentModifyRespDto;
import com.sparta.twitNation.util.api.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "댓글 API", description = "댓글 관련 API ")
public interface CommentControllerDocs {
    @Operation(summary = "댓글 생성", description = "게시글 한 건에 대한 댓글을 작성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다")
    })
    public ResponseEntity<ApiResult<CommentCreateRespDto>> createComment(@PathVariable(name = "postId") Long postId,
                                                                         @RequestBody @Valid CommentCreateReqDto commentCreateReqDto,
                                                                         @AuthenticationPrincipal LoginUser loginUser);

    @Operation(summary = "댓글 수정", description = "한 건의 대한 댓글을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "403", description = "해당 댓글을 수정할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 댓글입니다"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다")
    })
    public ResponseEntity<ApiResult<CommentModifyRespDto>> updateComment(@PathVariable(name = "postId") Long postId,
                                                                         @PathVariable(name = "commentId") Long commentId,
                                                                         @RequestBody @Valid CommentModifyReqDto commentModifyReqDto,
                                                                         @AuthenticationPrincipal LoginUser loginUser);

    @Operation(summary = "댓글 삭제", description = "한 건의 대한 댓글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "403", description = "해당 댓글을 수정할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 댓글입니다"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다")
    })
    public ResponseEntity<ApiResult<CommentDeleteRespDto>> deleteComment(@PathVariable(name = "postId") Long postId,
                                                                         @PathVariable(name = "commentId") Long commentId,
                                                                         @AuthenticationPrincipal LoginUser loginUser);
}
