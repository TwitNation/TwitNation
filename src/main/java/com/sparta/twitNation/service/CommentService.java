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
import com.sparta.twitNation.ex.ErrorCode;
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
                () -> new CustomApiException(ErrorCode.POST_NOT_FOUND)
        );

        Comment comment = commentRepository.save(commentReqDto.toEntity(post, loginUser.getUser()));

        return new CommentCreateRespDto(comment.getPost().getId(), comment.getId());
    }

    @Transactional
    public CommentModifyRespDto updateComment(Long postId, Long commentId, CommentModifyReqDto commentModifyReqDto, LoginUser loginUser) {

        Long userId = loginUser.getUser().getId();

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomApiException(ErrorCode.POST_NOT_FOUND)
        );


        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomApiException(ErrorCode.COMMENT_NOT_FOUND)
        );

        if (!comment.isWrittenBy(userId)) {
            throw new CustomApiException(ErrorCode.COMMENT_FORBIDEN);
        }

        comment.modify(commentModifyReqDto.content());

        Comment updatedComment = commentRepository.saveAndFlush(comment);

        return new CommentModifyRespDto(updatedComment.getPost().getId(), updatedComment.getId(), updatedComment.getLastModifiedAt());
    }

    @Transactional
    public CommentDeleteRespDto deleteComment(Long postId, Long commentId, LoginUser loginUser) {
        Long userId = loginUser.getUser().getId();

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomApiException(ErrorCode.POST_NOT_FOUND)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomApiException(ErrorCode.COMMENT_NOT_FOUND)
        );

        if (!comment.isWrittenBy(userId)) {
            throw new CustomApiException(ErrorCode.COMMENT_FORBIDEN);
        }

        commentRepository.deleteById(comment.getId());

        return new CommentDeleteRespDto(comment.getId());
    }
}
