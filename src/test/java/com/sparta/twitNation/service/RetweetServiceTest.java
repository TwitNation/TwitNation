package com.sparta.twitNation.service;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.config.jwt.JwtProcess;
import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.post.PostRepository;
import com.sparta.twitNation.domain.retweet.Retweet;
import com.sparta.twitNation.domain.retweet.RetweetRepository;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.dto.post.req.PostCreateReqDto;
import com.sparta.twitNation.dto.post.resp.PostCreateRespDto;
import com.sparta.twitNation.dto.retweet.resp.RetweetToggleRespDto;
import com.sparta.twitNation.ex.CustomApiException;
import com.sparta.twitNation.ex.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class RetweetServiceTest {

    @InjectMocks
    private RetweetService retweetService;

    @Mock
    private RetweetRepository retweetRepository;

    @Mock
    private PostRepository postRepository;


    @Test
    void toggleRetweet_CreateRetweet() {
        Long userId = 3L;
        Long postId = 3L;
        User user = User.builder().
                id(userId).
                email("test@test.com").
                nickname("test").
                password("test").
                build(); // LoginUser 안의 User
        Post post = Post.builder().
                id(postId).
                content("it is testContent.").
                user(user).
                build(); // 테스트에 사용할 Post
        LoginUser loginUser = new LoginUser(user);
        Retweet retweet = new Retweet(post, user);


        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(retweetRepository.findByPostAndUser(post, user)).thenReturn(Optional.empty());
        when(retweetRepository.save(any(Retweet.class))).thenReturn(retweet);

        //when
        RetweetToggleRespDto result = retweetService.toggleRetweet(postId, loginUser);

        //then
        assertNotNull(result);
        verify(retweetRepository).save(any(Retweet.class));
    }

    @Test
    void toggleRetweet_DeleteRetweet() {
        Long userId = 3L;
        Long postId = 3L;
        User user = User.builder().
                id(userId).
                email("test@test.com").
                nickname("test").
                password("test")
                .build();
        Post post = Post.builder().
                id(postId).
                content("ddd").
                user(user).
                build(); // 테스트에 사용할 Post
        LoginUser loginUser = new LoginUser(user);
        Retweet retweet = new Retweet(post, user);



        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(retweetRepository.findByPostAndUser(post, user)).thenReturn(Optional.of(retweet));

        RetweetToggleRespDto result = retweetService.toggleRetweet(postId, loginUser);

    }
}