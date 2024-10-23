package com.sparta.twitNation.dto.follow.resp;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowCreateRespDto {

    private Long id;
    private boolean isFollowed;

    public FollowCreateRespDto(Long id, boolean isFollowed) {
        this.id = id;
        this.isFollowed = isFollowed;
    }


}
