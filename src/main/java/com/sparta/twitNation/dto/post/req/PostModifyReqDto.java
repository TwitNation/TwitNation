package com.sparta.twitNation.dto.post.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

public record PostModifyReqDto(
        @NotBlank(message = "내용을 작성해야 합니다")
        @Length(min = 1, max = 1024, message = "게시글은 최소 1자 최대 1024자로 작성해야 합니다")
        String content) {
}
