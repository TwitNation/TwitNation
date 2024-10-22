package com.sparta.twitNation.service;

import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.domain.user.UserRepository;
import com.sparta.twitNation.dto.user.req.UserCreateReqDto;
import com.sparta.twitNation.dto.user.resp.UserCreateRespDto;
import com.sparta.twitNation.dto.user.resp.UserEditPageRespDto;
import com.sparta.twitNation.ex.CustomApiException;
import com.sparta.twitNation.ex.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserCreateRespDto register(UserCreateReqDto dto) {
        String encodedPassword = passwordEncoder.encode(dto.password());
        User user = new User(dto.passwordEncoded(encodedPassword));
        Optional<User> userOP = userRepository.findByEmail(user.getEmail());
        if (userOP.isPresent()) {
            throw new CustomApiException(ErrorCode.ALREADY_USER_EXIST);
        }

        return new UserCreateRespDto(userRepository.save(user).getId());
    }

    public UserEditPageRespDto editList(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.USER_NOT_FOUND));

        return new UserEditPageRespDto(findUser);
    }


}
