package com.sparta.twitNation.dto.comment.req;

import com.sparta.twitNation.domain.comment.Comment;
import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.user.User;

public record CommentCreateReqDto(String content){
    public Comment toEntity(Post post, User user) {
        return Comment.builder()
                .content(this.content)
                .post(post)
                .user(user)
                .build();
    }
}
