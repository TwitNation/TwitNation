package com.sparta.twitNation.domain.bookmark;

import com.sparta.twitNation.domain.base.BaseEntity;
import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "bookmarks")
public class Bookmark extends BaseEntity {

    //private boolean isBookmarked;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;



    // User를 포함한 명시적인 생성자 추가
    @Builder
    public Bookmark(Post post, User user) {
        this.post = post;
        this.user = user;
    }


}
