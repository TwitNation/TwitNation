package com.sparta.twitNation.dto.comment.req;

import com.sparta.twitNation.domain.comment.Comment;
import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CommentModifyReqDto(@NotBlank(message = "내용을 작성해야 합니다")
                                  @Length(min = 1, max = 512, message = "게시글은 최소 1자 최대 512자로 작성해야 합니다")
                                  @Schema(description = "댓글 내용을 작성합니다.", required = true)
                                  String content) {
    public Comment toEntity(Post post, User user) {
        return Comment.builder()
                .content(this.content)
                .post(post)
                .user(user)
                .build();
    }
}
