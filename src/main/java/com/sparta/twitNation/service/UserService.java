package com.sparta.twitNation.service;

import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.domain.user.UserRepository;
import com.sparta.twitNation.dto.user.req.UserCreateReqDto;
import com.sparta.twitNation.dto.user.resp.UserCreateRespDto;
import com.sparta.twitNation.ex.CustomApiException;
import com.sparta.twitNation.ex.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Long addUser(UserCreateReqDto dto) {
        String encodedPassword = passwordEncoder.encode(dto.password());
        UserCreateReqDto reqDto = dto.passwordEncoded(encodedPassword);
        User user = new User(reqDto);
        return userRepository.save(user).getId();
    }

    @Transactional
    public UserCreateRespDto register(UserCreateReqDto dto) {
        String encodedPassword = passwordEncoder.encode(dto.password());
        User user = new User(dto.passwordEncoded(encodedPassword));
        Optional<User> userOP = userRepository.findByEmail(user.getEmail());
        if (userOP.isPresent()) {
            throw new CustomApiException(ErrorCode.ALREADY_USER_EXIST);
        }

        return new UserCreateRespDto(userRepository.save(user).getId());
    }

}
