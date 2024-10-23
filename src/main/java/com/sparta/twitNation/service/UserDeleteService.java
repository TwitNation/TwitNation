package com.sparta.twitNation.service;

import com.sparta.twitNation.domain.bookmark.BookmarkRepository;
import com.sparta.twitNation.domain.comment.CommentRepository;
import com.sparta.twitNation.domain.follow.FollowRepository;
import com.sparta.twitNation.domain.like.LikeRepository;
import com.sparta.twitNation.domain.post.PostRepository;
import com.sparta.twitNation.domain.retweet.RetweetRepository;
import com.sparta.twitNation.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDeleteService {
    private final PostRepository postRepository;
    private final BookmarkRepository bookmarkRepository;
    private final CommentRepository commentRepository;
    private final FollowRepository followRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final RetweetRepository retweetRepository;

    @Transactional
    public void deleteUser(Long userId) {
        retweetRepository.deleteByUserId(userId);
        likeRepository.deleteByUserId(userId);
        bookmarkRepository.deleteByUserId(userId);
        followRepository.deleteByFollowerId(userId);
        commentRepository.deleteByUserId(userId);
        postRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);
    }
}
