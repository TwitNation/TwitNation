package com.sparta.twitNation.controller;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.dto.user.req.UserCreateReqDto;
import com.sparta.twitNation.dto.user.req.UserUpdateReqDto;
import com.sparta.twitNation.dto.user.resp.UserCreateRespDto;
import com.sparta.twitNation.dto.user.resp.UserEditPageRespDto;
import com.sparta.twitNation.dto.user.resp.UserUpdateRespDto;
import com.sparta.twitNation.service.UserService;
import com.sparta.twitNation.util.api.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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

        String profileImgUrl = null;
        // S3 생성 로직 ...

        UserCreateRespDto RespDto = userService.register(dto);
        ApiResult<UserCreateRespDto> success = ApiResult.success(RespDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(success);
    }

    @GetMapping("/api/user/profile")
    public ResponseEntity<ApiResult<UserEditPageRespDto>> editPage(@AuthenticationPrincipal LoginUser loginUser) {
        UserEditPageRespDto dto = userService.editList(loginUser.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(dto));
    }

    @PatchMapping("/api/user/profile")
    public ResponseEntity<ApiResult<UserUpdateRespDto>> updateUserInfo(
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestBody UserUpdateReqDto dto) {
        UserUpdateRespDto respDto = userService.updateUser(loginUser.getId(), dto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(respDto));
    }


}
