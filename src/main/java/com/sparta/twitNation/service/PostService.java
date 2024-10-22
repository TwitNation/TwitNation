package com.sparta.twitNation.service;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.domain.comment.CommentRepository;
import com.sparta.twitNation.domain.like.LikeRepository;
import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.post.PostRepository;
import com.sparta.twitNation.domain.retweet.RetweetRepository;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.domain.user.UserRepository;
import com.sparta.twitNation.dto.post.req.PostCreateReqDto;
import com.sparta.twitNation.dto.post.req.PostModifyReqDto;
import com.sparta.twitNation.dto.post.resp.PostCreateRespDto;
import com.sparta.twitNation.dto.post.resp.PostModifyRespDto;
import com.sparta.twitNation.dto.post.resp.PostReadPageRespDto;
import com.sparta.twitNation.dto.post.resp.UserPostsRespDto;
import com.sparta.twitNation.ex.CustomApiException;
import com.sparta.twitNation.ex.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // 게시물 생성
    @Transactional
    public PostCreateRespDto createPost(PostCreateReqDto postCreateReqDto, LoginUser loginUser) {
        Long userId = loginUser.getUser().getId();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException(ErrorCode.USER_NOT_FOUND)
        );
        Post post = postRepository.save(postCreateReqDto.toEntity(user));
        return new PostCreateRespDto(post);
    }

    // 게시물 수정
    @Transactional
    public PostModifyRespDto modifyPost(PostModifyReqDto postModifyReqDto, Long postId, LoginUser loginUser) {
        Long userId = loginUser.getUser().getId();
        userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException(ErrorCode.USER_NOT_FOUND)
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomApiException(ErrorCode.POST_NOT_FOUND)
        );
        if (!post.getUser().getId().equals(userId)) {
            throw new CustomApiException(ErrorCode.POST_FORBIDDEN);
        }
        post.modify(postModifyReqDto.content());
        log.info("유저 ID {}: 게시글 ID {} 수정 완료", userId, postId);
        return new PostModifyRespDto(post);
    }

    // 특정 유저의 게시물 조회
    @Transactional(readOnly = true)
    public UserPostsRespDto readPostsBy(final Long userId, final int page, final int limit) {
        final User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException(ErrorCode.POST_NOT_FOUND));
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