package com.sparta.twitNation.controller;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.dto.bookmark.req.BookmarkPostDto;
import com.sparta.twitNation.dto.bookmark.resp.BookmarkCreateRespDto;
import com.sparta.twitNation.dto.bookmark.resp.BookmarkViewRespDto;
import com.sparta.twitNation.dto.follow.resp.FollowCreateRespDto;
import com.sparta.twitNation.dto.follow.resp.FollowerViewRespDto;
import com.sparta.twitNation.util.api.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "팔로우 API", description = "팔로우 관련 API ")
public interface FollowControllerDocs {
    @Operation(summary = "팔로우 상태 변경 ", description = "유저를 팔로우에 추가/삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팔로우 추가/삭제 완료", content = @Content(schema =
            @Schema(implementation = FollowCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Authorization 헤더 재확인 바람", content = @Content(schema =
            @Schema(implementation = FollowCreateRespDto.class), mediaType = "application/json"))
    })
    ResponseEntity<ApiResult<FollowCreateRespDto>> changeFollowState(@PathVariable(name = "postId") Long postId, @AuthenticationPrincipal LoginUser loginUser);

    @Operation(summary = "팔로우 조회", description = "팔로우한 유저 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팔로워 조회 완료", content = @Content(schema =
            @Schema(implementation = FollowerViewRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "자기 자신은 팔로우 불가", content = @Content(schema =
            @Schema(implementation = FollowerViewRespDto.class), mediaType = "application/json"))
    })
    ResponseEntity<ApiResult<FollowerViewRespDto>> getFollowers(
            @RequestParam(defaultValue = "0") @Parameter(description = "페이지 번호") int page ,
            @RequestParam(defaultValue = "10") @Parameter(description = "페이지에 나갈 크기") int limit,
            @AuthenticationPrincipal LoginUser loginUser
    );
}
