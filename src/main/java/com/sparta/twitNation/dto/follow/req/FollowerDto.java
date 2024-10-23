package com.sparta.twitNation.dto.follow.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FollowerDto {
    private Long userId;
    private String userNickname;
    private String userProfileImg;

    public FollowerDto(Long userId, String userNickname, String userProfileImg) {
        this.userId = userId;
        this.userNickname = userNickname;
        this.userProfileImg = userProfileImg;

    }
}
