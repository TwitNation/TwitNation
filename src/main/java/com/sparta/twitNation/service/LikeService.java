package com.sparta.twitNation.service;

import com.sparta.twitNation.domain.comment.CommentRepository;
import com.sparta.twitNation.domain.like.Like;
import com.sparta.twitNation.domain.like.LikeCustomRepository;
import com.sparta.twitNation.domain.like.LikeRepository;
import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.post.PostRepository;
import com.sparta.twitNation.domain.retweet.RetweetRepository;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.dto.like.resp.LikeCreateRespDto;
import com.sparta.twitNation.dto.like.resp.LikeReadPageRespDto;
import com.sparta.twitNation.ex.CustomApiException;
import com.sparta.twitNation.ex.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final LikeCustomRepository likeCustomRepository;

    public LikeCreateRespDto toggleLike(User user, Long postId) {
        Optional<Like> likeOP = likeRepository.findByPostId(postId);

        if (likeOP.isPresent()) {
            likeRepository.delete(likeOP.get());
            return new LikeCreateRespDto(postId, false);
        }

        Post findPost = postRepository.findById(postId).orElseThrow(() ->
                new CustomApiException(ErrorCode.POST_NOT_FOUND));

        Like like = new Like(findPost, user);
        likeRepository.save(like);

        return new LikeCreateRespDto(postId, true);
    }

    public Page<LikeReadPageRespDto> likePosts(Long userId, Pageable pageable) {
        return likeCustomRepository.searchLikes(userId, pageable);
    }

}