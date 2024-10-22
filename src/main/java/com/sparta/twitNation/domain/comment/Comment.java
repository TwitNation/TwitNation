package com.sparta.twitNation.domain.comment;

import com.sparta.twitNation.domain.base.BaseEntity;
import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.dto.comment.req.CommentModifyReqDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 512)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Comment(Long id, String content, Post post, User user) {
        this.id = id;
        this.content = content;
        this.post = post;
        this.user = user;
    }


    public void modify(String content) {
        if (content != null) {
            this.content = content;
        }
    }

    public boolean isWrittenBy(Long userId) {
        return this.user.getId().equals(userId);
    }
}
