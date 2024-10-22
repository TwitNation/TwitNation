package com.sparta.twitNation.dto.user.resp;

import com.sparta.twitNation.domain.user.User;
import lombok.Builder;

public record UserEditPageRespDto (
        String nickname,
        String email,
        String bio,
        String password,
        String profileImg
){

    public UserEditPageRespDto(User user){
        this(user.getNickname(), user.getEmail(), user.getBio(), user.getPassword(), user.getProfileImg());
    }
}
