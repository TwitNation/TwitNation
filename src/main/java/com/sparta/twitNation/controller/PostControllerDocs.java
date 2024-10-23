package com.sparta.twitNation.controller;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.dto.post.req.PostCreateReqDto;
import com.sparta.twitNation.dto.post.resp.PostCreateRespDto;
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
import org.springframework.web.bind.annotation.RequestBody;
@Tag(name = "게시글 API", description = "게시글 관련 기능 API ")
public interface PostControllerDocs {

    @Operation(summary = "게시글 생성 ", description = "게시글 생성 기능: jwt 토큰 사용")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 생성 성공",content = @Content(schema = @Schema(implementation = PostCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Authorization 헤더가 누락되었습니다",content = @Content(schema = @Schema(implementation = PostCreateRespDto.class), mediaType = "application/json")),

    })
    ResponseEntity<ApiResult<PostCreateRespDto>> createPost(@RequestBody @Valid PostCreateReqDto postCreateReqDto, @AuthenticationPrincipal LoginUser loginUser);
}
