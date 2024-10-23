package com.sparta.twitNation.controller;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.dto.bookmark.req.BookmarkPostDto;
import com.sparta.twitNation.dto.bookmark.resp.BookmarkCreateRespDto;
import com.sparta.twitNation.dto.bookmark.resp.BookmarkViewRespDto;
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

@Tag(name = "북마크 API", description = "북마크 관련 API ")
public interface BookmarkControllerDocs {
    @Operation(summary = "북마크 상태 변경 ", description = "게시글을 북마크에 추가/삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "북마크 생성 성공", content = @Content(schema = @Schema(implementation = BookmarkCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패", content = @Content(schema = @Schema(implementation = BookmarkCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다", content = @Content(schema = @Schema(implementation = BookmarkCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "권한이 필요합니다", content = @Content(schema = @Schema(implementation = BookmarkCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부에 오류가 발생헀습니다. 잠시 후에 시도해주세요", content = @Content(schema = @Schema(implementation = BookmarkCreateRespDto.class), mediaType = "application/json"))
    })
    ResponseEntity<ApiResult<BookmarkCreateRespDto>> changeBookmarkState(@PathVariable(name = "postId") Long postId, @AuthenticationPrincipal LoginUser loginUser);

    @Operation(summary = "북마크 조회", description = "북마크한 게시글의 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "북마크 페이지 내의 게시글 정보 조회 성공", content = @Content(schema = @Schema(implementation = BookmarkViewRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "유효성 검사 실패", content = @Content(schema = @Schema(implementation = BookmarkViewRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다", content = @Content(schema = @Schema(implementation = BookmarkViewRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "권한이 필요합니다", content = @Content(schema = @Schema(implementation = BookmarkViewRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부에 오류가 발생헀습니다. 잠시 후에 시도해주세요", content = @Content(schema = @Schema(implementation = BookmarkViewRespDto.class), mediaType = "application/json"))
    })
    ResponseEntity<ApiResult<BookmarkViewRespDto>> getBookmarks(
            @RequestParam(defaultValue = "0") @Parameter(description = "페이지 번호") int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "페이지에 나갈 크기") int limit,
            @AuthenticationPrincipal LoginUser loginUser
    );
}
