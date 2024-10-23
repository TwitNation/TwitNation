package com.sparta.twitNation.domain.user;

import com.sparta.twitNation.domain.base.BaseEntity;
import com.sparta.twitNation.dto.user.req.UserCreateReqDto;
<<<<<<< HEAD
import com.sparta.twitNation.domain.post.Post;
=======
import com.sparta.twitNation.dto.user.req.UserUpdateReqDto;
>>>>>>> 986e081a762a214ebf568e7d3c3c839504f2d7d0
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    public User(UserCreateReqDto dto) {
        this.email = dto.email();
        this.bio = dto.bio();
        this.profileImg = dto.profileImg();
        this.nickname = dto.nickname();
        this.password = dto.password();
        this.role = UserRole.USER;
    }

<<<<<<< HEAD
//    public static User createTestUser() {
//        User user = new User();
//        user.setId(1L); // 임의의 ID
////        user.setNickname("user1"); // 임의의 사용자 이름
////        user.setPassword("1");
////        user.setUsername("user1");
//        user.setProfileImg("기본 이미지");
//
//        return user;
//    }

=======
    public void changeInfo(UserUpdateReqDto dto) {
        this.email = dto.email();
        this.password = dto.password();
        this.nickname = dto.nickname();
        this.bio = dto.bio();
    }
>>>>>>> 986e081a762a214ebf568e7d3c3c839504f2d7d0
}
