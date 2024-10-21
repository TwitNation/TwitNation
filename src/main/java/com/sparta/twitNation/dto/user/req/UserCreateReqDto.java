package com.sparta.twitNation.dto.user.req;

import com.sparta.twitNation.dto.user.resp.UserCreateRespDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Setter
public record UserCreateReqDto(
        @NotBlank(message = "이메일을 입력헤주세요.")
        @Email(message = "정확한 이메일 형식으로 입력해주세요. ")
        String email,
        @NotBlank
        @Size(min = 8, max = 20, message = "패스워드 글자 수는 60자까지만 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
        String password,

        @NotBlank(message = "닉네임을 입력해주세요.")
        @Size(max = 20, message = "닉네임 글자 수는 20자까지만 입력해주세요. ")
        String nickname,

        @NotBlank(message = "자기소개를 입력해주세요.")
        @Size(max = 512)
        String bio,

        String profileImg
) {

    public UserCreateReqDto withProfileImg(String profileImg) {
        return new UserCreateReqDto(this.email, this.password, this.nickname, this.bio, profileImg);
    }

    public UserCreateReqDto passwordEncoded(String password) {
        return new UserCreateReqDto(this.email, this.password, this.nickname, this.bio, this.profileImg);
    }
}
