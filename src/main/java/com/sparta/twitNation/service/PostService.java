package com.sparta.twitNation.service;

import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.post.PostRepository;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.domain.user.UserRepository;
import com.sparta.twitNation.dto.post.req.PostCreateReqDto;
import com.sparta.twitNation.dto.post.resp.PostCreateRespDto;
import com.sparta.twitNation.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostCreateRespDto createPost(PostCreateReqDto postCreateReqDto, Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException("존재하지 않는 유저입니다")
        );
        Post post = postRepository.save(postCreateReqDto.toEntity(user));
        return new PostCreateRespDto(post);
    }

}
