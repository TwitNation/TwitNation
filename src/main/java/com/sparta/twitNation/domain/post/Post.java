package com.sparta.twitNation.domain.post;


import com.sparta.twitNation.domain.base.BaseEntity;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.ex.CustomApiException;
import com.sparta.twitNation.ex.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "posts")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Post(Long id, String content, User user) {
        this.id = id;
        this.content = content;
        this.user = user;
    }

    public void modify(String content) {
        this.content = content;
    }

    public void validatePostOwner(Long userId){
        if (!this.getUser().getId().equals(userId)) {
            throw new CustomApiException(ErrorCode.POST_FORBIDDEN);
        }
    }
}
