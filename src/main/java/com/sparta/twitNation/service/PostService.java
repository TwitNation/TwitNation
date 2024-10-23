package com.sparta.twitNation.service;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.domain.bookmark.BookmarkRepository;
import com.sparta.twitNation.domain.comment.Comment;
import com.sparta.twitNation.domain.comment.CommentRepository;
import com.sparta.twitNation.domain.like.LikeRepository;
import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.post.PostRepository;
import com.sparta.twitNation.domain.post.dto.PostDetailWithUser;
import com.sparta.twitNation.domain.post.dto.PostWithDetails;
import com.sparta.twitNation.domain.post.dto.PostWithUser;
import com.sparta.twitNation.domain.retweet.RetweetRepository;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.domain.user.UserRepository;
import com.sparta.twitNation.dto.comment.resp.CommentListRespDto;
import com.sparta.twitNation.dto.post.req.PostCreateReqDto;
import com.sparta.twitNation.dto.post.req.PostModifyReqDto;
import com.sparta.twitNation.dto.post.resp.PostCreateRespDto;
import com.sparta.twitNation.dto.post.resp.PostDeleteRespDto;
import com.sparta.twitNation.dto.post.resp.PostDetailRespDto;
import com.sparta.twitNation.dto.post.resp.PostModifyRespDto;
import com.sparta.twitNation.dto.post.resp.PostsReadPageRespDto;
import com.sparta.twitNation.dto.post.resp.PostsSearchPageRespDto;
import com.sparta.twitNation.dto.post.resp.PostsSearchRespDto;
import com.sparta.twitNation.dto.post.resp.UserPostsRespDto;
import com.sparta.twitNation.ex.CustomApiException;
import com.sparta.twitNation.ex.ErrorCode;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final RetweetRepository retweetRepository;
    private final BookmarkRepository bookmarkRepository;
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
        post.validatePostOwner(userId);
        post.modify(postModifyReqDto.content());
        log.info("유저 ID {}: 게시글 ID {} 수정 완료", userId, postId);
        return new PostModifyRespDto(post);
    }

    @Transactional
    public PostDeleteRespDto deletePost(Long postId, LoginUser loginUser) {
        Long userId = loginUser.getUser().getId();
        userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException(ErrorCode.USER_NOT_FOUND)
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomApiException(ErrorCode.POST_NOT_FOUND)
        );
        //작성자 검증
        post.validatePostOwner(userId);

        //댓글, 좋아요, 리트윗, 북마크 삭제
        deleteRelatedEntities(postId);

        //게시글 삭제
        postRepository.delete(post);
        log.info("게시글 ID {} 삭제 완료", postId);
        return new PostDeleteRespDto(postId);
    }


    private void deleteRelatedEntities(Long postId) {
        int deletedCommentCnt = commentRepository.deleteCommentsByPostId(postId);
        int deletedLikeCnt = likeRepository.deleteLikesByPostId(postId);
        int deletedRetweetCnt = retweetRepository.deleteRetweetsByPostId(postId);
        int deletedBookmarkCnt = bookmarkRepository.deleteBookmarksByPostId(postId);

        log.info("게시글 ID {}: 삭제된 댓글 {}, 좋아요 {}, 리트윗 {}, 북마크 {}",
                postId, deletedCommentCnt, deletedLikeCnt, deletedRetweetCnt, deletedBookmarkCnt);
    }

    // 특정 유저의 게시물 조회
    @Transactional(readOnly = true)
    public UserPostsRespDto readPostsBy(final Long userId, final int page, final int limit) {
        final User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException(ErrorCode.USER_NOT_FOUND));

        final Page<PostWithUser> posts = postRepository.findAllByUser(user, PageRequest.of(page, limit));

        return UserPostsRespDto.from(posts);
    }

    @Transactional(readOnly = true)
    public PostsReadPageRespDto readPosts(final int page, final int limit) {
        final Page<PostWithDetails> posts = postRepository.findAllWithDetails(PageRequest.of(page, limit));
        return PostsReadPageRespDto.from(posts);
    }

    //게시글 단건 조회
    public PostDetailRespDto getPostById(Long postId, LoginUser loginUser) {
        Long userId = loginUser.getUser().getId();
        userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException(ErrorCode.USER_NOT_FOUND)
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomApiException(ErrorCode.POST_NOT_FOUND)
        );
        //게시글과 작성자 조회
        PostDetailWithUser postDetailWithUser = postRepository.getPostDetailWithUser(post);

        //좋아요, 댓글, 리트윗 개수 조회
        int likeCount = likeRepository.countByPost(post);
        int commentCount = commentRepository.countByPost(post);
        int retweetCount = retweetRepository.countByPost(post);

        return new PostDetailRespDto(postDetailWithUser, likeCount, commentCount, retweetCount);
    }

    //특정 게시글의 댓글 목록 조회
    public CommentListRespDto getCommentsByPostId(Long postId, User user, int page, int limit) {
        Long userId = user.getId();
        userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException(ErrorCode.USER_NOT_FOUND)
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomApiException(ErrorCode.POST_NOT_FOUND)
        );
        //해당 게시글의 댓글 리스트 (with 댓글 작성자 정보) 조회
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Comment> commentPage = commentRepository.findAllByPost(post, pageable);
        return new CommentListRespDto(commentPage);
    }

    public PostsSearchPageRespDto searchKeyword(
            final String sort,
            final String keyword,
            final int page,
            final int limit,
            final LocalDateTime startModifiedAt,
            final LocalDateTime endModifiedAt
    ) {
        final Page<PostsSearchRespDto> posts = postRepository.searchByNicknameAndKeyword(
                sort,
                keyword,
                startModifiedAt,
                endModifiedAt,
                PageRequest.of(page, limit)
        );

        return PostsSearchPageRespDto.from(posts);
    }
}


