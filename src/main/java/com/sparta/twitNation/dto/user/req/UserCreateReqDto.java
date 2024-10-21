package com.sparta.twitNation.dto.user.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateReqDto (@NotBlank @Email String email, String password, String nickname, String bio, String profileImg) {
}
