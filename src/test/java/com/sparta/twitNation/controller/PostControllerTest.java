package com.sparta.twitNation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.config.jwt.JwtProcess;
import com.sparta.twitNation.config.jwt.JwtVo;
import com.sparta.twitNation.domain.post.PostRepository;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.domain.user.UserRepository;
import com.sparta.twitNation.dto.post.req.PostCreateReqDto;
import com.sparta.twitNation.dto.post.req.PostModifyReqDto;
import com.sparta.twitNation.dto.post.resp.PostCreateRespDto;
import com.sparta.twitNation.service.PostService;
import com.sparta.twitNation.util.dummy.DummyObject;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.awaitility.Awaitility.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
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

    private String token;

    @BeforeEach
    void setUp() {
        String password = "password";
        User user = User.builder().id(1L).nickname("userAAAAAAAA").email("userA@email.com").password(passwordEncoder.encode(password)).build();
        userRepository.save(user);
        postRepository.save(newPost(user));
        em.clear();

        LoginUser loginUser = new LoginUser(user);
        token = jwtProcess.create(loginUser);
    }

    @WithUserDetails(value = "userA", setupBefore = TestExecutionEvent.TEST_EXECUTION)
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

    @WithUserDetails(value = "userA", setupBefore = TestExecutionEvent.TEST_EXECUTION)
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

    @WithUserDetails(value = "userA", setupBefore = TestExecutionEvent.TEST_EXECUTION)
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

    @WithUserDetails(value = "userA", setupBefore = TestExecutionEvent.TEST_EXECUTION)
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

}