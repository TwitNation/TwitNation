package com.sparta.twitNation.service;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.domain.comment.Comment;
import com.sparta.twitNation.domain.comment.CommentRepository;
import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.post.PostRepository;
import com.sparta.twitNation.dto.comment.req.CommentCreateReqDto;
import com.sparta.twitNation.dto.comment.req.CommentModifyReqDto;
import com.sparta.twitNation.dto.comment.resp.CommentCreateRespDto;
import com.sparta.twitNation.dto.comment.resp.CommentDeleteRespDto;
import com.sparta.twitNation.dto.comment.resp.CommentModifyRespDto;
import com.sparta.twitNation.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    @Transactional
    public CommentCreateRespDto createComment(CommentCreateReqDto commentReqDto, LoginUser loginUser, Long postId) {
        Long userId = loginUser.getUser().getId();

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomApiException("존재하지 않는 트윗입니다.", HttpStatus.NOT_FOUND.value())
        );

        Comment comment = commentRepository.save(commentReqDto.toEntity(post, loginUser.getUser()));

        return new CommentCreateRespDto(comment.getPost().getId(), comment.getId());
    }

    @Transactional
    public CommentModifyRespDto updateComment(Long postId, Long commentId, CommentModifyReqDto commentModifyReqDto, LoginUser loginUser) {

        Long userId = loginUser.getUser().getId();

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomApiException("존재하지 않는 트윗입니다.", HttpStatus.NOT_FOUND.value())
        );


        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomApiException("댓글을 찾을 수 없습니다", HttpStatus.NOT_FOUND.value())
        );

        if (!comment.getUser().getId().equals(userId)) {
            throw new CustomApiException("해당 댓글에 접근할 권한이 없습니다", HttpStatus.UNAUTHORIZED.value());
        }

        comment.modify(commentModifyReqDto.content());

        commentRepository.saveAndFlush(comment);

        return new CommentModifyRespDto(comment.getPost().getId(), comment.getId(), comment.getLastModifiedAt());
    }

    @Transactional
    public CommentDeleteRespDto deleteComment(Long postId, Long commentId, LoginUser loginUser) {
        Long userId = loginUser.getUser().getId();

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomApiException("존재하지 않는 트윗입니다.", HttpStatus.NOT_FOUND.value())
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomApiException("댓글을 찾을 수 없습니다", HttpStatus.NOT_FOUND.value())
        );

        if (!comment.getUser().getId().equals(userId)) {
            throw new CustomApiException("해당 댓글에 접근할 권한이 없습니다", HttpStatus.UNAUTHORIZED.value());
        }

        commentRepository.deleteById(comment.getId());

        return new CommentDeleteRespDto(comment.getId(), true);
    }
}
