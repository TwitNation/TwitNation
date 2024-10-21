package com.sparta.twitNation.dto.user.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateReqDto(
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Size(max = 60)
        String password,

        @NotBlank(message = "Nickname is required")
        String nickname,

        @NotBlank
        @Size(max = 512)
        String bio,
        String profileImg
) {
}
