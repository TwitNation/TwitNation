package com.sparta.twitNation.controller;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.dto.comment.resp.CommentListRespDto;
import com.sparta.twitNation.dto.post.req.PostCreateReqDto;
import com.sparta.twitNation.dto.post.req.PostModifyReqDto;
import com.sparta.twitNation.dto.post.resp.*;
import com.sparta.twitNation.dto.user.resp.UserUpdateRespDto;
import com.sparta.twitNation.util.api.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Tag(name = "게시글 API", description = "게시글 관련 기능 API ")
public interface PostControllerDocs {

    @Operation(summary = "게시글 생성 ", description = "게시글을 생성합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 생성 성공",content = @Content(schema = @Schema(implementation = PostCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패",content = @Content(schema = @Schema(implementation = PostCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다",content = @Content(schema = @Schema(implementation = PostCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "권한이 필요합니다",content = @Content(schema = @Schema(implementation = PostCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다",content = @Content(schema = @Schema(implementation = PostCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부에 오류가 발생헀습니다. 잠시 후에 시도해주세요",content = @Content(schema = @Schema(implementation = PostCreateRespDto.class), mediaType = "application/json"))
    })
    ResponseEntity<ApiResult<PostCreateRespDto>> createPost(@RequestBody @Valid PostCreateReqDto postCreateReqDto, @AuthenticationPrincipal LoginUser loginUser);

    @Operation(summary = "게시글 수정 ", description = "게시글을 수정합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공",content = @Content(schema = @Schema(implementation = PostModifyRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패",content = @Content(schema = @Schema(implementation = PostModifyRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다",content = @Content(schema = @Schema(implementation = PostModifyRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "권한이 필요합니다",content = @Content(schema = @Schema(implementation = PostModifyRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다",content = @Content(schema = @Schema(implementation = PostModifyRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부에 오류가 발생헀습니다. 잠시 후에 시도해주세요",content = @Content(schema = @Schema(implementation = PostModifyRespDto.class), mediaType = "application/json"))
    })
    ResponseEntity<ApiResult<PostModifyRespDto>> modifyPost(@PathVariable(value = "postId") Long postId, @RequestBody @Valid PostModifyReqDto postModifyReqDto, @AuthenticationPrincipal LoginUser loginUser);

    @Operation(summary = "게시글 삭제 ", description = "게시글을 삭제합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공",content = @Content(schema = @Schema(implementation = PostDeleteRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패",content = @Content(schema = @Schema(implementation = PostDeleteRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다",content = @Content(schema = @Schema(implementation = PostDeleteRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "권한이 필요합니다",content = @Content(schema = @Schema(implementation = PostDeleteRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다",content = @Content(schema = @Schema(implementation = PostDeleteRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부에 오류가 발생헀습니다. 잠시 후에 시도해주세요",content = @Content(schema = @Schema(implementation = PostDeleteRespDto.class), mediaType = "application/json"))
    })
    ResponseEntity<ApiResult<PostDeleteRespDto>> deletePost(@PathVariable(value = "postId") Long postId, @AuthenticationPrincipal LoginUser loginUser);

    @Operation(summary = "유저 페이지 내의 게시글 정보 조회", description = "유저 페이지 내의 게시글 정보를 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 페이지 내의 게시글 정보 조회 성공",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "권한이 필요합니다",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부에 오류가 발생헀습니다. 잠시 후에 시도해주세요",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json"))
    })
    ResponseEntity<ApiResult<UserPostsRespDto>> readPostByUser(@PathVariable(value = "userId") final Long userId, @RequestParam(defaultValue = "0", value = "page") int page, @RequestParam(defaultValue = "10", value = "limit") int limit);


    @Operation(summary = "팔로우하는 유저들의 전체 게시글 조회", description = "팔로우하는 유저들의 전체 게시글을 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팔로우하는 유저들의 전체 게시글 조회 성공",content = @Content(schema = @Schema(implementation = PostsReadPageRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "권한이 필요합니다",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부에 오류가 발생헀습니다. 잠시 후에 시도해주세요",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json"))
    })
    ResponseEntity<ApiResult<PostsReadPageRespDto>> readPosts(@AuthenticationPrincipal final LoginUser loginUser, @RequestParam(defaultValue = "0", value = "page") int page, @RequestParam(defaultValue = "10", value = "limit") int limit);

    @Operation(summary = "게시글 단건 조회 (댓글 목록 X)", description = "게시글 단건을 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 단건 조회 성공",content = @Content(schema = @Schema(implementation = PostDetailRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "권한이 필요합니다",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부에 오류가 발생헀습니다. 잠시 후에 시도해주세요",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json"))
    })
    ResponseEntity<ApiResult<PostDetailRespDto>> getPostById(@PathVariable(value = "postId") Long postId, @AuthenticationPrincipal LoginUser loginUser);


    @Operation(summary = "게시글 단건의 댓글 목록 조회", description = "게시글 단건의 댓글 목록을 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 단건의 댓글 목록 조회 성공",content = @Content(schema = @Schema(implementation = CommentListRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "권한이 필요합니다",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부에 오류가 발생헀습니다. 잠시 후에 시도해주세요",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json"))
    })
    ResponseEntity<ApiResult<CommentListRespDto>> getCommentsByPostId(@PathVariable(value = "postId") Long postId, @RequestParam(defaultValue = "0", value = "page") @Min(0) int page, @RequestParam(defaultValue = "10", value = "limit") @Positive int limit, @AuthenticationPrincipal LoginUser loginUser);

    @Operation(summary = "검색", description = "게시글 내용 또는 유저를 검색합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "검색 성공",content = @Content(schema = @Schema(implementation = PostsSearchPageRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "권한이 필요합니다",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부에 오류가 발생헀습니다. 잠시 후에 시도해주세요",content = @Content(schema = @Schema(implementation = UserPostsRespDto.class), mediaType = "application/json"))
    })
    ResponseEntity<ApiResult<PostsSearchPageRespDto>> searchPosts(
            @RequestParam(defaultValue = "0") @Parameter(description = "페이지 번호") final int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "한 페이지에 나갈 크기") final int limit,
            @RequestParam(required = false) @Parameter(description = "검색할 키워드") final String keyword,
            @RequestParam(required = false) final LocalDateTime startModifiedAt,
            @RequestParam(required = false) final LocalDateTime endModifiedAt,
            @RequestParam(defaultValue = "lastModifiedAt, desc") final String sort
    );

}
