package com.sparta.twitNation.controller;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.dto.user.req.UserCreateReqDto;
import com.sparta.twitNation.dto.user.req.UserDeleteReqDto;
import com.sparta.twitNation.dto.user.req.UserUpdateReqDto;
import com.sparta.twitNation.dto.user.resp.*;
import com.sparta.twitNation.service.UserService;
import com.sparta.twitNation.util.api.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/join")
    public ResponseEntity<ApiResult<UserCreateRespDto>> joinUser(
            @RequestPart("user") @Valid UserCreateReqDto dto,
            @RequestPart(value = "profileImg", required = false) MultipartFile profileImg
    ) {
        UserCreateRespDto RespDto = userService.register(dto, profileImg);
        ApiResult<UserCreateRespDto> success = ApiResult.success(RespDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(success);
    }

    @GetMapping("/api/users/profile")
    public ResponseEntity<ApiResult<UserEditPageRespDto>> editPage(@AuthenticationPrincipal LoginUser loginUser) {
        UserEditPageRespDto dto = userService.editList(loginUser.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(dto));
    }

    @PatchMapping("/api/users/profile")
    public ResponseEntity<ApiResult<UserUpdateRespDto>> updateUserInfo(
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestBody @Valid UserUpdateReqDto dto
    ) {
        UserUpdateRespDto respDto = userService.updateUser(loginUser.getUser().getId(), dto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(respDto));
    }

    @GetMapping("/api/users/profile/{userId}")
    public ResponseEntity<ApiResult<UserInfoRespDto>> myPage(@PathVariable Long userId) {
        UserInfoRespDto respDto = userService.userInfo(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(respDto));
    }

    @DeleteMapping("/api/users")
    public ResponseEntity<ApiResult<UserDeleteRespDto>> resign(
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestBody UserDeleteReqDto dto)
    {
        UserDeleteRespDto respDto = userService.deleteUser(dto, loginUser);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(respDto));
    }
}
