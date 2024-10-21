package com.sparta.twitNation.service;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.domain.comment.Comment;
import com.sparta.twitNation.domain.comment.CommentRepository;
import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.post.PostRepository;
import com.sparta.twitNation.dto.comment.req.CommentCreateReqDto;
import com.sparta.twitNation.dto.comment.resp.CommentCreateRespDto;
import com.sparta.twitNation.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentCreateRespDto createComment(CommentCreateReqDto commentReqDto, LoginUser loginUser, Long postId) {
        Long userId = loginUser.getUser().getId();

        if (userId == null) {
            log.error("인증 실패: userId is null");
            throw new CustomApiException("인증 정보가 유효하지 않습니다", HttpStatus.UNAUTHORIZED.value());
        }

        Post post = postRepository.findById(postId).orElseThrow(
                ()->new CustomApiException("존재하지 않는 트윗입니다.", HttpStatus.NOT_FOUND.value())
        );

        Comment comment = commentRepository.save(commentReqDto.toEntity(post,loginUser.getUser()));

        return new CommentCreateRespDto(comment.getPost().getId() ,comment.getId());
    }


}
