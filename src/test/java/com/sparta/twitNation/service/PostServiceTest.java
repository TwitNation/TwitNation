package com.sparta.twitNation.service;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.domain.bookmark.BookmarkRepository;
import com.sparta.twitNation.domain.comment.Comment;
import com.sparta.twitNation.domain.comment.CommentRepository;
import com.sparta.twitNation.domain.like.Like;
import com.sparta.twitNation.domain.like.LikeRepository;
import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.post.PostRepository;
import com.sparta.twitNation.domain.post.dto.PageDetailWithUser;
import com.sparta.twitNation.domain.retweet.Retweet;
import com.sparta.twitNation.domain.retweet.RetweetRepository;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.domain.user.UserRepository;
import com.sparta.twitNation.dto.post.req.PostCreateReqDto;
import com.sparta.twitNation.dto.post.req.PostModifyReqDto;
import com.sparta.twitNation.dto.post.resp.PostCreateRespDto;
import com.sparta.twitNation.dto.post.resp.PostDeleteRespDto;
import com.sparta.twitNation.dto.post.resp.PostDetailRespDto;
import com.sparta.twitNation.dto.post.resp.PostModifyRespDto;
import com.sparta.twitNation.ex.CustomApiException;
import com.sparta.twitNation.ex.ErrorCode;
import com.sparta.twitNation.util.dummy.DummyObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PostServiceTest extends DummyObject {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private BookmarkRepository bookmarkRepository;

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private RetweetRepository retweetRepository;

    private LoginUser loginUser;
    private User mockUser;
    private Post mockPost;


    @BeforeEach
    void setUp() {
        mockUser = User.builder().id(1L).build();
        loginUser = new LoginUser(mockUser);
        mockPost = Post.builder().id(1L).user(mockUser).build();

    }

    @Test
    void success_createPost_test(){
        //given
        Long userId = 1L;
        String content = "test content";

        User user = User.builder().id(userId).build();
        Post post = Post.builder().user(user).content(content).build();
        LoginUser loginUser = new LoginUser(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.save(any())).thenReturn(post);

        //when
        PostCreateRespDto result = postService.createPost(new PostCreateReqDto(content), loginUser);

        //then
        assertNotNull(result);
        verify(postRepository).save(any(Post.class));
    }

    @Test
    void fail_createPost_userIdIsNull_test(){
        LoginUser loginUser = new LoginUser(User.builder().build());

        assertThrows(CustomApiException.class, () -> {
            postService.createPost(new PostCreateReqDto("test content"), loginUser);
        });
    }

    @Test
    void fail_createPost_userNotFound_test(){
        //given
        Long userId = 1L;
        User user = User.builder().id(userId).build();
        LoginUser loginUser = new LoginUser(user);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(CustomApiException.class, () ->{
            postService.createPost(new PostCreateReqDto("test content"), loginUser);
        });
    }

    @Test
    void success_modifyPost_test(){
        //given
        Long userId = 1L;
        Long postId = 5L;
        String content = "test content";

        User user = User.builder().id(userId).build();
        Post post = Post.builder().user(user).id(postId).content(content).build();
        LoginUser loginUser = new LoginUser(user);
        PostModifyReqDto postModifyReqDto = new PostModifyReqDto("수정 완료");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        //when
        PostModifyRespDto result = postService.modifyPost(postModifyReqDto, postId, loginUser);

        //then
        assertThat(result).isNotNull();
        assertThat(result.content()).isEqualTo("수정 완료");
        assertThat(post.getContent()).isEqualTo("수정 완료");
        verify(userRepository, times(1)).findById(userId);
        verify(postRepository, times(1)).findById(postId);

    }

    @Test
    void fail_modifyPost_test(){
        //given
        Long userId = 1L;
        Long postId = 5L;
        String content = "test content";

        User user = User.builder().id(userId).build();
        LoginUser loginUser = new LoginUser(user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        CustomApiException exception = assertThrows(CustomApiException.class, () -> {
            postService.modifyPost(new PostModifyReqDto("test content"), postId, loginUser);
        });

        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 게시글입니다");
        assertThat(exception.getErrorCode().getStatus()).isEqualTo(404);


    }

    @Test
    void fail_modifyPost_userHasNoPermission_test(){
        //given
        Long userId = 1L;
        Long postId = 5L;
        String content = "test content";

        User user = User.builder().id(userId).build();
        LoginUser loginUser = new LoginUser(user);

        User postOwner = User.builder().id(2L).build();
        Post post = Post.builder().id(postId).user(postOwner).content(content).build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(loginUser.getUser()));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // when & then
        CustomApiException exception = assertThrows(CustomApiException.class, () -> {
            postService.modifyPost(new PostModifyReqDto("수정 내용"), postId, loginUser);
        });

        assertThat(exception.getMessage()).isEqualTo("해당 게시글을 수정할 권한이 없습니다");
        assertThat(exception.getErrorCode().getStatus()).isEqualTo(403);
    }

    @Test
    void fail_modifyPost_userNotFound_test() {
        // given
        Long userId = 1L;
        Long postId = 5L;

        User user = User.builder().id(userId).build();
        LoginUser loginUser = new LoginUser(user);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // when  then
        CustomApiException exception = assertThrows(CustomApiException.class, () -> {
            postService.modifyPost(new PostModifyReqDto("수정 내용"), postId, loginUser);
        });

        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 유저입니다");
        assertThat(exception.getErrorCode().getStatus()).isEqualTo(404);
    }

    @Test
    void success_deletePost_test() throws Exception{
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(postRepository.findById(1L)).thenReturn(Optional.of(mockPost));
        when(commentRepository.deleteCommentsByPostId(1L)).thenReturn(5);
        when(likeRepository.deleteLikesByPostId(1L)).thenReturn(3);
        when(retweetRepository.deleteRetweetsByPostId(1L)).thenReturn(2);
        when(bookmarkRepository.deleteBookmarksByPostId(1L)).thenReturn(1);

        PostDeleteRespDto result = postService.deletePost(mockPost.getId(), loginUser);

        verify(userRepository).findById(1L);
        verify(postRepository).findById(1L);
        verify(commentRepository).deleteCommentsByPostId(1L);
        verify(likeRepository).deleteLikesByPostId(1L);
        verify(retweetRepository).deleteRetweetsByPostId(1L);
        verify(bookmarkRepository).deleteBookmarksByPostId(1L);
    }

    @Test
    void fail_deletePost_forbidden_test() {
        User anotherUser = User.builder().id(2L).build();
        LoginUser anotherLoginUser = new LoginUser(anotherUser);

        when(userRepository.findById(any())).thenReturn(Optional.of(anotherUser));
        when(postRepository.findById(1L)).thenReturn(Optional.of(mockPost));

        CustomApiException exception = assertThrows(CustomApiException.class, () -> {
            postService.deletePost(1L, anotherLoginUser);
        });

        assertEquals(ErrorCode.POST_FORBIDDEN, exception.getErrorCode());
    }

    @Test
    void success_getPostById_test(){

        List<Comment> commentList = new ArrayList<>();
        List<Like> likeList = new ArrayList<>();
        List<Retweet> retweetList = new ArrayList<>();
        for(int i = 0;i<10;i++){
            retweetList.add(mockRetweet(mockPost));
            commentList.add(mockComment(mockPost));
            likeList.add(mockLike(mockPost));
        }

        PageDetailWithUser mockPostDetailWithUser = new PageDetailWithUser(mockPost.getId(), mockUser.getId(),
                mockUser.getNickname(), mockPost.getContent(), mockPost.getLastModifiedAt(), mockUser.getProfileImg()
                );

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(postRepository.findById(1L)).thenReturn(Optional.of(mockPost));
        when(postRepository.getPostDetailWithUser(mockPost)).thenReturn(mockPostDetailWithUser);
        when(likeRepository.countByPost(mockPost)).thenReturn(likeList.size());
        when(commentRepository.countByPost(mockPost)).thenReturn(commentList.size());
        when(retweetRepository.countByPost(mockPost)).thenReturn(retweetList.size());

        PostDetailRespDto result = postService.getPostById(mockPost.getId(), loginUser);

        assertNotNull(result);
        assertEquals(likeList.size(), result.likeCount());
        assertEquals(commentList.size(), result.commentCount());
        assertEquals(retweetList.size(), result.retweetCount());
        assertEquals(mockPost.getId(), result.postId());

    }

    @Test
    void fail_getPostById_postNotFound_test() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        //예외
        CustomApiException exception = assertThrows(CustomApiException.class,
                () -> postService.getPostById(1L, loginUser));

        assertEquals(ErrorCode.POST_NOT_FOUND, exception.getErrorCode());
    }
}