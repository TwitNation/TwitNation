package com.sparta.twitNation.dto.follow.resp;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FollowingReadRespDto {
    private Long userId;
    private String nickname;
    private String profileImg;

    @QueryProjection
    public FollowingReadRespDto(Long userId, String nickname, String profileImg) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }
}
