package com.sparta.twitNation.controller;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.dto.like.resp.LikeCreateRespDto;
import com.sparta.twitNation.dto.like.resp.LikeReadPageListRespDto;
import com.sparta.twitNation.dto.post.resp.PostsReadPageRespDto;
import com.sparta.twitNation.dto.post.resp.UserPostsRespDto;
import com.sparta.twitNation.util.api.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "좋아요 API", description = "좋아요 관련 기능 API ")
public interface LikeControllerDocs {

    @Operation(summary = "좋아요 토글(좋아요하기/좋아요취소)", description = "한 번의 요청으로 좋아요 요청/해제에 대한 결과를 리턴합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "좋아요 성공"),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패", content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다", content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "권한이 필요합니다", content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다", content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부에 오류가 발생헀습니다. 잠시 후에 시도해주세요", content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json"))
    })
    @PostMapping("/api/posts/{postId}/likes")
    public ResponseEntity<ApiResult<LikeCreateRespDto>> likeOrUnlike(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable Long postId);

    @Operation(summary = "좋아요 게시글 조회", description = "좋아요한 게시글을 볼 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요한 전체 게시글 조회 성공", content = @Content(schema = @Schema(implementation = PostsReadPageRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패", content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다", content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "권한이 필요합니다", content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다", content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부에 오류가 발생헀습니다. 잠시 후에 시도해주세요", content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json"))
    })
    @GetMapping("/api/users/likes")
    public ResponseEntity<ApiResult<LikeReadPageListRespDto>> likePage(
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size);
}
