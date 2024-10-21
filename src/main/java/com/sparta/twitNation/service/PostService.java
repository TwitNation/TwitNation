package com.sparta.twitNation.service;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.post.PostRepository;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.domain.user.UserRepository;
import com.sparta.twitNation.dto.post.req.PostCreateReqDto;
import com.sparta.twitNation.dto.post.req.PostModifyReqDto;
import com.sparta.twitNation.dto.post.resp.PostCreateRespDto;
import com.sparta.twitNation.dto.post.resp.PostModifyRespDto;
import com.sparta.twitNation.ex.CustomApiException;
import com.sparta.twitNation.ex.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Transactional
    public PostCreateRespDto createPost(PostCreateReqDto postCreateReqDto, LoginUser loginUser){
        Long userId = loginUser.getUser().getId();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException(ErrorCode.USER_NOT_FOUND)
        );
        Post post = postRepository.save(postCreateReqDto.toEntity(user));
        return new PostCreateRespDto(post);
    }

    @Transactional
    public PostModifyRespDto modifyPost(PostModifyReqDto postModifyReqDto, Long postId, LoginUser loginUser){
        Long userId = loginUser.getUser().getId();
        userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException(ErrorCode.USER_NOT_FOUND)
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomApiException(ErrorCode.POST_NOT_FOUND)
        );
        post.modify(postModifyReqDto.content());
        log.info("유저 ID {}: 게시글 ID {} 수정 완료", userId, postId);
        return new PostModifyRespDto(post);
    }



}
