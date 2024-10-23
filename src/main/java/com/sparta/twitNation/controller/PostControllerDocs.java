package com.sparta.twitNation.controller;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.dto.post.req.PostCreateReqDto;
import com.sparta.twitNation.dto.post.req.PostModifyReqDto;
import com.sparta.twitNation.dto.post.resp.PostCreateRespDto;
import com.sparta.twitNation.dto.post.resp.PostModifyRespDto;
import com.sparta.twitNation.dto.user.resp.UserUpdateRespDto;
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
@Tag(name = "게시글 API", description = "게시글 관련 기능 API ")
public interface PostControllerDocs {

    @Operation(summary = "게시글 생성 ", description = "게시글을 생성합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 생성 성공",content = @Content(schema = @Schema(implementation = PostCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패",content = @Content(schema = @Schema(implementation = PostCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다",content = @Content(schema = @Schema(implementation = PostCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다",content = @Content(schema = @Schema(implementation = PostCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부에 오류가 발생헀습니다. 잠시 후에 시도해주세요",content = @Content(schema = @Schema(implementation = PostCreateRespDto.class), mediaType = "application/json")),
    })
    ResponseEntity<ApiResult<PostCreateRespDto>> createPost(@RequestBody @Valid PostCreateReqDto postCreateReqDto, @AuthenticationPrincipal LoginUser loginUser);

    @Operation(summary = "게시글 수정 ", description = "게시글을 수정합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공",content = @Content(schema = @Schema(implementation = PostCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패",content = @Content(schema = @Schema(implementation = PostCreateRespDto.class), mediaType = "application/json")),

    })
    ResponseEntity<ApiResult<PostModifyRespDto>> modifyPost(@PathVariable(value = "postId") Long postId, @RequestBody @Valid PostModifyReqDto postModifyReqDto, @AuthenticationPrincipal LoginUser loginUser);

}
