package com.sparta.twitNation.dto.post.req;

import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.user.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

public record PostCreateReqDto(
        @NotBlank(message = "내용을 작성해야 합니다")
        @Length(min = 1, max = 1024, message = "게시글은 최소 1자 최대 1024자로 작성해야 합니다")
        String content
) {
    public Post toEntity(User user) {
        return Post.builder()
                .content(this.content())
                .user(user)
                .build();
    }
}
