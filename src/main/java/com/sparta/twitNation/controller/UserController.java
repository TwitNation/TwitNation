package com.sparta.twitNation.controller;

import com.sparta.twitNation.dto.user.req.UserCreateReqDto;
import com.sparta.twitNation.dto.user.resp.UserCreateRespDto;
import com.sparta.twitNation.service.UserService;
import com.sparta.twitNation.util.api.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/join")
    public ResponseEntity<ApiResult<UserCreateRespDto>> joinUser(
            @RequestPart @Valid UserCreateReqDto dto,
            @RequestPart(value = "profileImg", required = false) MultipartFile profileImg
            ) {

        String profileImgUrl = null;
        // S3 생성 로직 ...

        UserCreateRespDto RespDto = userService.register(dto);
        ApiResult<UserCreateRespDto> success = ApiResult.success(RespDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(success);
    }
}
