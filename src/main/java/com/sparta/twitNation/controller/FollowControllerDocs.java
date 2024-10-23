package com.sparta.twitNation.controller;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.dto.bookmark.req.BookmarkPostDto;
import com.sparta.twitNation.dto.bookmark.resp.BookmarkCreateRespDto;
import com.sparta.twitNation.dto.bookmark.resp.BookmarkViewRespDto;
import com.sparta.twitNation.dto.follow.resp.FollowCreateRespDto;
import com.sparta.twitNation.dto.follow.resp.FollowerViewRespDto;
import com.sparta.twitNation.dto.follow.resp.FollowingReadPageRespDto;
import com.sparta.twitNation.dto.post.resp.PostCreateRespDto;
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

    @Operation(summary = "팔로잉 조회", description = "팔로잉한 유저 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팔로잉 조회 성공", content = @Content(schema = @Schema(implementation = FollowingReadPageRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "유효성 검사 실패", content = @Content(schema = @Schema(implementation = FollowingReadPageRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다", content = @Content(schema = @Schema(implementation = FollowingReadPageRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "권한이 필요합니다",content = @Content(schema = @Schema(implementation = FollowingReadPageRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부에 오류가 발생헀습니다. 잠시 후에 시도해주세요",content = @Content(schema = @Schema(implementation = FollowingReadPageRespDto.class), mediaType = "application/json"))

    })
    ResponseEntity<ApiResult<FollowingReadPageRespDto>> followings(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") @Parameter(description = "페이지 번호") int page ,
            @RequestParam(defaultValue = "10") @Parameter(description = "페이지에 나갈 크기") int limit
    );


    @Operation(summary = "팔로우 상태 변경 ", description = "유저를 팔로우에 추가/삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팔로우 추가/삭제 성공", content = @Content(schema = @Schema(implementation = FollowCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패",content = @Content(schema = @Schema(implementation = FollowCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다", content = @Content(schema = @Schema(implementation = FollowCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "권한이 필요합니다",content = @Content(schema = @Schema(implementation = FollowCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부에 오류가 발생헀습니다. 잠시 후에 시도해주세요",content = @Content(schema = @Schema(implementation = FollowCreateRespDto.class), mediaType = "application/json"))
    })
    ResponseEntity<ApiResult<FollowCreateRespDto>> changeFollowState(@PathVariable(name = "postId") Long postId, @AuthenticationPrincipal LoginUser loginUser);

    @Operation(summary = "팔로우 조회", description = "팔로우한 유저 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팔로워 조회 성공", content = @Content(schema = @Schema(implementation = FollowerViewRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "유효성 검사 실패", content = @Content(schema = @Schema(implementation = FollowerViewRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다", content = @Content(schema = @Schema(implementation = FollowerViewRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "권한이 필요합니다",content = @Content(schema = @Schema(implementation = FollowerViewRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부에 오류가 발생헀습니다. 잠시 후에 시도해주세요",content = @Content(schema = @Schema(implementation = FollowerViewRespDto.class), mediaType = "application/json"))

    })
    ResponseEntity<ApiResult<FollowerViewRespDto>> getFollowers(
            @RequestParam(defaultValue = "0") @Parameter(description = "페이지 번호") int page ,
            @RequestParam(defaultValue = "10") @Parameter(description = "페이지에 나갈 크기") int limit,
            @PathVariable Long userId
    );




}
