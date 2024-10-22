package com.sparta.twitNation.service;

import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.domain.user.UserRepository;
import com.sparta.twitNation.dto.user.req.UserCreateReqDto;
import com.sparta.twitNation.dto.user.resp.UserCreateRespDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    @Test
    void createSuccessTest() {
        // given
        UserCreateReqDto dto = new UserCreateReqDto("asdf@naver.com", "1234", "Spring", "hello word!", null);
        String password = dto.password();
        User user = new User(dto);

        // studding
        when(passwordEncoder.encode(password)).thenReturn("1234");
        when(userRepository.save(any())).thenReturn(user);

        // when
        UserCreateRespDto respDto = userService.register(dto);

        // then
        assertThat(respDto).isNotNull();
        verify(passwordEncoder, times(1)).encode(any());
        verify(userRepository, times(1)).save(any());
    }
}