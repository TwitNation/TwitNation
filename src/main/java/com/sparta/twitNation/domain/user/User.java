package com.sparta.twitNation.domain.user;

import com.sparta.twitNation.domain.base.BaseEntity;
import com.sparta.twitNation.dto.user.req.UserCreateReqDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(length = 512)
    private String bio;

    private String profileImg;

    @Column(nullable = false, length = 60)
    private String password;

    @Builder
    public User(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public User(UserCreateReqDto dto, String password) {
        this.email = dto.email();
        this.bio = dto.bio();
        this.profileImg = dto.profileImg();
        this.nickname = dto.nickname();
        this.password = password;
    }
}
