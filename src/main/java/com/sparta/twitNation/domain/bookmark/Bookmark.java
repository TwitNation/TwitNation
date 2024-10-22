package com.sparta.twitNation.domain.bookmark;

import com.sparta.twitNation.domain.base.BaseEntity;
import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "bookmarks")
public class Bookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Bookmark(Long id, Post post, User user) {
        this.id = id;
        this.post = post;
        this.user = user;
    }
}
