package com.sparta.twitNation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.config.jwt.JwtProcess;
import com.sparta.twitNation.config.jwt.JwtVo;
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
import com.sparta.twitNation.dto.post.resp.PostDetailRespDto;
import com.sparta.twitNation.service.PostService;
import com.sparta.twitNation.util.dummy.DummyObject;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class PostControllerTest extends DummyObject {

    @Autowired
    private PostService postService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager em;

    @Autowired
    private JwtProcess jwtProcess;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private RetweetRepository retweetRepository;

    private String token;

    private LoginUser loginUser;
    private User mockUser;
    private Post mockPost;

    @BeforeEach
    void setUp(){
        User user = newUser();
        mockUser = userRepository.save(user);

        Post post = newPost(user);
        mockPost = postRepository.save(post);

        em.clear();

        LoginUser loginUser = new LoginUser(user);
        token = jwtProcess.create(loginUser);
    }

    @WithUserDetails(value = "userA",setupBefore = TestExecutionEvent.TEST_EXECUTION )
    @Test
    void success_createPost_test() throws Exception {

        //given
        PostCreateReqDto postCreateReqDto = new PostCreateReqDto("test content");
        String requestBody = om.writeValueAsString(postCreateReqDto);

        //when
        ResultActions resultActions = mvc.perform(post("/api/posts")
                        .header(JwtVo.HEADER, token)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);
    }

    @WithUserDetails(value = "userA",setupBefore = TestExecutionEvent.TEST_EXECUTION )
    @Test
    void fail_createPost_invalid_length_test() throws Exception {

        //given
        PostCreateReqDto postCreateReqDto = new PostCreateReqDto("");
        String requestBody = om.writeValueAsString(postCreateReqDto);

        //when
        ResultActions resultActions = mvc.perform(post("/api/posts")
                        .header(JwtVo.HEADER, token)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.apiError.msg").value("유효성 검사 실패"))
                .andExpect(jsonPath("$.apiError.status").value(400));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);
    }

    @WithUserDetails(value = "userA",setupBefore = TestExecutionEvent.TEST_EXECUTION )
    @Test
    void fail_createPost_invalid_notBlank_test() throws Exception {

        //given
        PostCreateReqDto postCreateReqDto = new PostCreateReqDto(" ");
        String requestBody = om.writeValueAsString(postCreateReqDto);

        //when
        ResultActions resultActions = mvc.perform(post("/api/posts")
                        .header(JwtVo.HEADER, token)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.apiError.msg").value("유효성 검사 실패"))
                .andExpect(jsonPath("$.apiError.status").value(400));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);
    }
    @WithUserDetails(value = "userA", setupBefore = TestExecutionEvent.TEST_EXECUTION )
    @Test
    void success_modifyPost_test() throws Exception {
        //given
        PostModifyReqDto postModifyReqDto = new PostModifyReqDto("수정 성공");
        String requestBody = om.writeValueAsString(postModifyReqDto);

        //when then
        ResultActions resultActions = mvc.perform(put("/api/posts/{postId}", 1L)
                        .header(JwtVo.HEADER, token)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").value("수정 성공"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);
    }

    @WithUserDetails(value = "userA", setupBefore = TestExecutionEvent.TEST_EXECUTION )
    @Test
    void success_deletePost_test() throws Exception {
        //given

        //when then
        ResultActions resultActions = mvc.perform(delete("/api/posts/{postId}", 1L)
                        .header(JwtVo.HEADER, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);
    }


    @Test
    @WithUserDetails(value = "userA@email.com", setupBefore = TestExecutionEvent.TEST_EXECUTION )
    void success_getPostById_test() throws Exception {

        PageDetailWithUser mockPostDetailWithUser = new PageDetailWithUser(mockPost.getId(), mockUser.getId(),
                mockUser.getNickname(), mockPost.getContent(), mockPost.getLastModifiedAt(), mockUser.getProfileImg()
        );
        PostDetailRespDto mockResponse = new PostDetailRespDto(mockPostDetailWithUser, 0,0,0);


        ResultActions resultActions = mvc.perform(get("/api/posts/{postId}", mockPost.getId())
                        .header(JwtVo.HEADER, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.likeCount").value(0))
                .andExpect(jsonPath("$.data.commentCount").value(0))
                .andExpect(jsonPath("$.data.retweetCount").value(0))
                .andExpect(jsonPath("$.data.postId").value(mockPost.getId()));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);
    }

}