package com.sparta.twitNation.domain.user;

import com.sparta.twitNation.domain.base.BaseEntity;
import com.sparta.twitNation.dto.user.req.UserCreateReqDto;
import com.sparta.twitNation.dto.user.req.UserUpdateReqDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; //추후에 삭제해주세요!!

    @Column(unique = true, nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(length = 512)
    private String bio;

    private String profileImg;

    @Column(nullable = false, length = 60)
    private String password;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Builder
    public User(Long id, String email, String password, String nickname, UserRole role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    public User(UserCreateReqDto dto, String imgUrl) {
        this.email = dto.email();
        this.bio = dto.bio();
        this.profileImg = imgUrl;
        this.nickname = dto.nickname();
        this.password = dto.password();
        this.role = UserRole.USER;
    }

    public void changeInfo(UserUpdateReqDto dto) {
        this.email = dto.email();
        this.password = dto.password();
        this.nickname = dto.nickname();
        this.bio = dto.bio();
    }

    public void updateProfileImg(String profileImg){
        this.profileImg = profileImg;
    }
}
