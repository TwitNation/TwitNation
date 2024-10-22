package com.sparta.twitNation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.sparta.twitNation.dto.post.resp.PostsReadPageRespDto;
import com.sparta.twitNation.dto.post.resp.UserPostsRespDto;
import com.sparta.twitNation.ex.CustomApiException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private RetweetRepository retweetRepository;

    @Test
    void success_createPost_test() {
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
    void fail_createPost_userIdIsNull_test() {
        LoginUser loginUser = new LoginUser(User.builder().build());

        assertThrows(CustomApiException.class, () -> {
            postService.createPost(new PostCreateReqDto("test content"), loginUser);
        });
    }

    @Test
    void fail_createPost_userNotFound_test() {
        //given
        Long userId = 1L;
        User user = User.builder().id(userId).build();
        LoginUser loginUser = new LoginUser(user);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(CustomApiException.class, () -> {
            postService.createPost(new PostCreateReqDto("test content"), loginUser);
        });
    }

    @Test
    void success_modifyPost_test() {
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
    void fail_modifyPost_test() {
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
    void fail_modifyPost_userHasNoPermission_test() {
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
    @DisplayName("유저가 작성한 게시물을 페이징 형식으로 조회")
    void read_posts_by_user_test() {
        // given
        final Long userId = 1L;
        final User user = User.builder().id(userId).build();

        final Post post1 = Post.builder().id(1L).content("테스트 내용 1").user(user).build();
        final Post post2 = Post.builder().id(1L).content("테스트 내용 2").user(user).build();
        final Post post3 = Post.builder().id(1L).content("테스트 내용 3").user(user).build();

        final Page<Post> postPage = new PageImpl<>(
                List.of(post1, post2, post3), PageRequest.of(
                0, 1, Sort.by(Sort.Direction.DESC, "lastModifiedAt")), 3);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.findByUser(any(User.class), any(PageRequest.class))).thenReturn(postPage);
        when(likeRepository.countByPost(post1)).thenReturn(5);
        when(commentRepository.countByPost(post1)).thenReturn(3);
        when(retweetRepository.countByPost(post1)).thenReturn(2);

        // when
        final UserPostsRespDto response = postService.readPostsBy(1L, 0, 1);

        // then
        assertThat(response.elementsCount()).isEqualTo(3);
        assertThat(response.currentPage()).isEqualTo(0);
        assertThat(response.nextPage()).isEqualTo(1);
        assertThat(response.pageCount()).isEqualTo(3);
        assertThat(response.nextPageBool()).isEqualTo(true);
        assertThat(response.pageSize()).isEqualTo(1);

        assertThat(response.posts().get(0).userNickname()).isEqualTo(null);
        assertThat(response.posts().get(0).content()).isEqualTo("테스트 내용 1");
        assertThat(response.posts().get(0).likeCount()).isEqualTo(5);
        assertThat(response.posts().get(0).commentCount()).isEqualTo(3);
        assertThat(response.posts().get(0).retweetCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("전체 게시물 조회 테스트")
    void read_all_posts_test() {
        // given
        final Long userId = 1L;
        final User user = User.builder().id(userId).build();

        final Post post1 = Post.builder().id(1L).content("테스트 내용 1").user(user).build();
        final Post post2 = Post.builder().id(1L).content("테스트 내용 2").user(user).build();
        final Post post3 = Post.builder().id(1L).content("테스트 내용 3").user(user).build();

        final Page<Post> postPage = new PageImpl<>(
                List.of(post1, post2, post3), PageRequest.of(
                0, 1, Sort.by(Sort.Direction.DESC, "lastModifiedAt")), 3);

        when(postRepository.findAll(any(PageRequest.class))).thenReturn(postPage);

        when(likeRepository.countByPost(post1)).thenReturn(5);
        when(commentRepository.countByPost(post1)).thenReturn(3);
        when(retweetRepository.countByPost(post1)).thenReturn(2);

        when(likeRepository.countByPost(post2)).thenReturn(1);
        when(commentRepository.countByPost(post2)).thenReturn(2);
        when(retweetRepository.countByPost(post2)).thenReturn(3);

        when(likeRepository.countByPost(post3)).thenReturn(4);
        when(commentRepository.countByPost(post3)).thenReturn(5);
        when(retweetRepository.countByPost(post3)).thenReturn(6);

        // when
        final PostsReadPageRespDto response = postService.readPosts(0, 1);

        // then
        assertThat(response.elementsCount()).isEqualTo(3);
        assertThat(response.currentPage()).isEqualTo(0);
        assertThat(response.nextPage()).isEqualTo(1);
        assertThat(response.pageCount()).isEqualTo(3);
        assertThat(response.nextPageBool()).isEqualTo(true);
        assertThat(response.pageSize()).isEqualTo(1);

        // 첫 번째 페이지
        assertThat(response.posts().get(0).userNickname()).isEqualTo(null);
        assertThat(response.posts().get(0).content()).isEqualTo("테스트 내용 1");
        assertThat(response.posts().get(0).likeCount()).isEqualTo(5);
        assertThat(response.posts().get(0).commentCount()).isEqualTo(3);
        assertThat(response.posts().get(0).retweetCount()).isEqualTo(2);

        // 두 번째 페이지
        assertThat(response.posts().get(1).userNickname()).isEqualTo(null);
        assertThat(response.posts().get(1).content()).isEqualTo("테스트 내용 2");
        assertThat(response.posts().get(1).likeCount()).isEqualTo(1);
        assertThat(response.posts().get(1).commentCount()).isEqualTo(2);
        assertThat(response.posts().get(1).retweetCount()).isEqualTo(3);

        // 세 번째 페이지
        assertThat(response.posts().get(2).userNickname()).isEqualTo(null);
        assertThat(response.posts().get(2).content()).isEqualTo("테스트 내용 3");
        assertThat(response.posts().get(2).likeCount()).isEqualTo(4);
        assertThat(response.posts().get(2).commentCount()).isEqualTo(5);
        assertThat(response.posts().get(2).retweetCount()).isEqualTo(6);
    }
}
