package com.sparta.twitNation.service;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.domain.user.UserRepository;
import com.sparta.twitNation.dto.user.req.UserCreateReqDto;
import com.sparta.twitNation.dto.user.req.UserDeleteReqDto;
import com.sparta.twitNation.dto.user.req.UserUpdateReqDto;
import com.sparta.twitNation.dto.user.resp.*;
import com.sparta.twitNation.ex.CustomApiException;
import com.sparta.twitNation.ex.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Service s3Service;

    @Transactional
    public UserCreateRespDto register(UserCreateReqDto dto, MultipartFile file) {
        Optional<User> userOP = userRepository.findByEmail(dto.email());
        if (userOP.isPresent()) {
            throw new CustomApiException(ErrorCode.ALREADY_USER_EXIST);
        }
        String encodedPassword = passwordEncoder.encode(dto.password());
        String imgUrl = s3Service.uploadImage(file);
        User user = new User(dto.passwordEncoded(encodedPassword), imgUrl);
        User savedUser = userRepository.save(user);
        return new UserCreateRespDto(savedUser.getId(), savedUser.getEmail());
    }

    public UserEditPageRespDto editList(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.USER_NOT_FOUND));

        return new UserEditPageRespDto(findUser);
    }

    @Transactional
    public UserUpdateRespDto updateUser(Long userId, UserUpdateReqDto dto) {
        String password = dto.password();
        String encodedPassword = passwordEncoder.encode(password);
        UserUpdateReqDto reqDto = dto.passwordEncoded(encodedPassword);

        User findUser = userRepository.findById(userId).orElseThrow(() ->
                new CustomApiException(ErrorCode.USER_NOT_FOUND));

        findUser.changeInfo(reqDto);
        return new UserUpdateRespDto(findUser);
    }

    public UserInfoRespDto userInfo(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() ->
                new CustomApiException(ErrorCode.USER_NOT_FOUND));

        return new UserInfoRespDto(findUser);
    }

    public UserDeleteRespDto deleteUser(UserDeleteReqDto dto, LoginUser loginUser) {
        User user = userRepository.findById(loginUser.getUser().getId()).orElseThrow(() ->
                new CustomApiException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new CustomApiException(ErrorCode.MISS_MATCHER_PASSWORD);
        }
        s3Service.deleteImage(user.getProfileImg());
        userRepository.deleteById(user.getId());
        SecurityContextHolder.clearContext();

        return new UserDeleteRespDto(user.getId());
    }
}
