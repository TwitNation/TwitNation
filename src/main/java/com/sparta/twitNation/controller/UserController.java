package com.sparta.twitNation.controller;

import com.sparta.twitNation.dto.user.req.UserCreateReqDto;
import com.sparta.twitNation.dto.user.resp.UserCreateRespDto;
import com.sparta.twitNation.service.UserService;
import com.sparta.twitNation.util.api.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/join")
    public ResponseEntity<ApiResult<UserCreateRespDto>> joinUser(@RequestBody @Valid UserCreateReqDto dto) {
        UserCreateRespDto RespDto = userService.addUser(dto);
        ApiResult<UserCreateRespDto> success = ApiResult.success(RespDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(success);
    }
}
