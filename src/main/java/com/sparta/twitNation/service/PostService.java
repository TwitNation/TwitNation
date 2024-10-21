package com.sparta.twitNation.service;

import com.sparta.twitNation.domain.comment.CommentRepository;
import com.sparta.twitNation.domain.like.LikeRepository;
import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.post.PostRepository;
import com.sparta.twitNation.domain.retweet.RetweetRepository;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.domain.user.UserRepository;
import com.sparta.twitNation.dto.post.resp.PostReadPageRespDto;
import com.sparta.twitNation.dto.post.resp.UserPostsRespDto;
import com.sparta.twitNation.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final RetweetRepository retweetRepository;

    @Transactional(readOnly = true)
    public UserPostsRespDto readPostsBy(final Long userId, final int page, final int limit) {
        final User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException("존재하지 않는 유저입니다", HttpStatus.NOT_FOUND.value()));
        final Page<Post> posts = postRepository.findByUser(user,
                PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "lastModifiedAt")));

        final Page<PostReadPageRespDto> response = posts.map(
                post -> {
                    final int likeCount = likeRepository.countByPost(post);
                    final int commentCount = commentRepository.countByPost(post);
                    final int retweetCount = retweetRepository.countByPost(post);
                    return PostReadPageRespDto.from(user, post, likeCount, commentCount, retweetCount);
                });

        return UserPostsRespDto.from(response);
    }
}
