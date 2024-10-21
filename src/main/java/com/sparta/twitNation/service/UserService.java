package com.sparta.twitNation.service;

import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.domain.user.UserRepository;
import com.sparta.twitNation.dto.user.req.UserCreateReqDto;
import com.sparta.twitNation.dto.user.resp.UserCreateRespDto;
import com.sparta.twitNation.util.api.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Long addUser(UserCreateReqDto dto) {
        String encodedPassword = passwordEncoder.encode(dto.password());
        User user = new User(dto, encodedPassword);
        return userRepository.save(user).getId();
    }


}
