package com.sparta.twitNation.service;

import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.domain.user.UserRepository;
import com.sparta.twitNation.dto.user.req.UserCreateReqDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void creatUserSuccessTest() {
        // given
        UserCreateReqDto dto = new UserCreateReqDto("asdf@naver.com", "1234", "Spring", "hello world!", null);
        String password = dto.password();
        UserCreateReqDto reqDto = dto.passwordEncoded(passwordEncoder.encode(password));
        User user = new User(reqDto);

        // when
        User savedUser = userRepository.save(user);

        //then
        assertThat(savedUser.getId()).isEqualTo(1L);
        assertThat(savedUser.getEmail()).isEqualTo("asdf@naver.com");
        assertThat(savedUser.getCreatedAt()).isBefore(LocalDateTime.now());
    }
}