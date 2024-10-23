package com.sparta.twitNation.controller;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.dto.user.req.UserCreateReqDto;
import com.sparta.twitNation.dto.user.req.UserDeleteReqDto;
import com.sparta.twitNation.dto.user.req.UserUpdateReqDto;
import com.sparta.twitNation.dto.user.resp.*;
import com.sparta.twitNation.util.api.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "유저 API", description = "유저 관련 API")
public interface UserControllerDocs {

    @Operation(summary = "회원 가입", description = "회원 가입을 위한 엔드포인트입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 생성 성공", content = @Content(schema = @Schema(implementation = UserCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패", content = @Content(schema = @Schema(implementation = UserCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다", content = @Content(schema = @Schema(implementation = UserCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "권한이 필요합니다", content = @Content(schema = @Schema(implementation = UserCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.", content = @Content(schema = @Schema(implementation = UserCreateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부에 오류가 발생헀습니다. 잠시 후에 시도해주세요", content = @Content(schema = @Schema(implementation = UserCreateRespDto.class), mediaType = "application/json"))
    })
    @RequestMapping(method = RequestMethod.POST, value = "/auth/join",
            consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ApiResult<UserCreateRespDto>> joinUser(
            @RequestPart("user") @Valid UserCreateReqDto dto,
            @RequestPart(value = "profileImg", required = false) MultipartFile profileImg);

    @Operation(summary = "프로필 조회", description = "로그인한 사용자의 프로필 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 수정 페이지 경로", content = @Content(schema = @Schema(implementation = UserEditPageRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패", content = @Content(schema = @Schema(implementation = UserEditPageRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다", content = @Content(schema = @Schema(implementation = UserEditPageRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "권한이 필요합니다", content = @Content(schema = @Schema(implementation = UserEditPageRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.", content = @Content(schema = @Schema(implementation = UserEditPageRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부에 오류가 발생헀습니다. 잠시 후에 시도해주세요", content = @Content(schema = @Schema(implementation = UserEditPageRespDto.class), mediaType = "application/json"))
    })
    @GetMapping("/api/users/profile")
    ResponseEntity<ApiResult<UserEditPageRespDto>> editPage(
            @AuthenticationPrincipal LoginUser loginUser);


    @Operation(summary = "프로필 수정", description = "사용자의 프로필 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 정보 수정", content = @Content(schema = @Schema(implementation = UserUpdateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패", content = @Content(schema = @Schema(implementation = UserUpdateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다", content = @Content(schema = @Schema(implementation = UserUpdateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "권한이 필요합니다", content = @Content(schema = @Schema(implementation = UserUpdateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다..", content = @Content(schema = @Schema(implementation = UserUpdateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부에 오류가 발생헀습니다. 잠시 후에 시도해주세요", content = @Content(schema = @Schema(implementation = UserUpdateRespDto.class), mediaType = "application/json"))
    })
    @PatchMapping("/api/users/profile")
    ResponseEntity<ApiResult<UserUpdateRespDto>> updateUserInfo(
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestBody @Valid UserUpdateReqDto dto);

    @Operation(summary = "사용자 페이지 조회", description = "특정 사용자의 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 페이지 조회 성공", content = @Content(schema = @Schema(implementation = UserInfoRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패", content = @Content(schema = @Schema(implementation = UserInfoRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다", content = @Content(schema = @Schema(implementation = UserInfoRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "권한이 필요합니다", content = @Content(schema = @Schema(implementation = UserInfoRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.", content = @Content(schema = @Schema(implementation = UserInfoRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부에 오류가 발생헀습니다. 잠시 후에 시도해주세요", content = @Content(schema = @Schema(implementation = UserInfoRespDto.class), mediaType = "application/json"))
    })
    @GetMapping("/api/users/profile/{userId}")
    ResponseEntity<ApiResult<UserInfoRespDto>> myPage(@PathVariable Long userId);


    @Operation(summary = "회원 탈퇴", description = "사용자가 회원 탈퇴를 요청합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 삭제 성공", content = @Content(schema = @Schema(implementation = UserDeleteRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패", content = @Content(schema = @Schema(implementation = UserDeleteRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다", content = @Content(schema = @Schema(implementation = UserDeleteRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "권한이 필요합니다", content = @Content(schema = @Schema(implementation = UserDeleteRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.", content = @Content(schema = @Schema(implementation = UserDeleteRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부에 오류가 발생헀습니다. 잠시 후에 시도해주세요", content = @Content(schema = @Schema(implementation = UserDeleteRespDto.class), mediaType = "application/json"))
    })
    @DeleteMapping("/api/users")
    ResponseEntity<ApiResult<UserDeleteRespDto>> resign(
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestBody UserDeleteReqDto dto);

    @Operation(summary = "프로필 이미지 수정", description = "사용자의 프로필 이미지를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 수정 성공", content = @Content(schema = @Schema(implementation = UserProfileImgUpdateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패", content = @Content(schema = @Schema(implementation = UserProfileImgUpdateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다", content = @Content(schema = @Schema(implementation = UserProfileImgUpdateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "권한이 필요합니다", content = @Content(schema = @Schema(implementation = UserProfileImgUpdateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자", content = @Content(schema = @Schema(implementation = UserProfileImgUpdateRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부에 오류가 발생헀습니다. 잠시 후에 시도해주세요", content = @Content(schema = @Schema(implementation = UserProfileImgUpdateRespDto.class), mediaType = "application/json"))
    })
    @PutMapping(value = "/api/users/profile/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ApiResult<UserProfileImgUpdateRespDto>> updateProfileImg(
            @RequestPart(value = "profileImg", required = false) MultipartFile profileImg,
            @AuthenticationPrincipal LoginUser loginUser);

    @Operation(summary = "프로필 이미지 삭제", description = "사용자의 프로필 이미지를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 삭제 성공", content = @Content(schema = @Schema(implementation = UserProfileImgDeleteRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패", content = @Content(schema = @Schema(implementation = UserProfileImgDeleteRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다", content = @Content(schema = @Schema(implementation = UserProfileImgDeleteRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "권한이 필요합니다", content = @Content(schema = @Schema(implementation = UserProfileImgDeleteRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.", content = @Content(schema = @Schema(implementation = UserProfileImgDeleteRespDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부에 오류가 발생헀습니다. 잠시 후에 시도해주세요", content = @Content(schema = @Schema(implementation = UserProfileImgDeleteRespDto.class), mediaType = "application/json"))
    })
    @DeleteMapping("/api/users/profile/image")
    ResponseEntity<ApiResult<UserProfileImgDeleteRespDto>> deleteProfileImg(
            @AuthenticationPrincipal LoginUser loginUser);
}
