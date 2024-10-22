package com.sparta.twitNation.service;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.post.PostRepository;
import com.sparta.twitNation.domain.retweet.Retweet;
import com.sparta.twitNation.domain.retweet.RetweetRepository;
import com.sparta.twitNation.dto.retweet.resp.RetweetToggleRespDto;
import com.sparta.twitNation.ex.CustomApiException;
import com.sparta.twitNation.ex.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RetweetService {

    private final RetweetRepository retweetRepository;

    private final PostRepository postRepository;

    @Transactional
    public RetweetToggleRespDto toggleRetweet(Long postId, LoginUser loginUser) {
        Long userId = loginUser.getUser().getId();

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomApiException(ErrorCode.POST_NOT_FOUND)
        );

        Optional<Retweet> retweetOp = retweetRepository.findByPostAndUser(post, loginUser.getUser());

        return retweetOp
                .map(retweet -> {
                    retweetRepository.delete(retweetOp.get());
                    return new RetweetToggleRespDto(postId, false);
                })
                .orElseGet(() -> {
                    Retweet retweet = new Retweet(post, loginUser.getUser());
                    retweetRepository.save(retweet);
                    return new RetweetToggleRespDto(postId, true);
                });
    }
}
