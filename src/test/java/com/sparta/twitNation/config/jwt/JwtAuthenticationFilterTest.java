package com.sparta.twitNation.config.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.twitNation.config.auth.dto.LoginReqDto;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.domain.user.UserRepository;
import com.sparta.twitNation.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc //mock 환경에 mvc가 new돼서 들어감
@ActiveProfiles("test")
class JwtAuthenticationFilterTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() throws  Exception{
        String password = "password";
        User user = User.builder().id(1L).nickname("userAAAAAAAA").email("userA@email.com").password(passwordEncoder.encode(password)).build();
        userRepository.save(user);
    }

    @Test
    void success_authentication_test() throws Exception {
        //given
        LoginReqDto loginReqDto = LoginReqDto.builder().email("asdf@naver.com").password("password").build();
        String requestBody = om.writeValueAsString(loginReqDto);
        System.out.println("requestBody = " + requestBody);

        //when
        ResultActions resultActions = mvc.perform(post("/auth/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        String jwtToken = resultActions.andReturn().getResponse().getHeader(JwtVo.HEADER);

        System.out.println("responseBody = " + responseBody);
        System.out.println("jwtToken = " + jwtToken);

        //then
        resultActions.andExpect(status().isOk());
        assertNotNull(jwtToken);
        assertTrue(jwtToken.startsWith(JwtVo.TOKEN_PREFIX));
        resultActions.andExpect(jsonPath("$.data.username").value("userA"));
    }

    @Test
    void fail_authentication_test() throws Exception {
        //given
        LoginReqDto loginReqDto = LoginReqDto.builder().email("asdf@naver.com").password("password1234").build();
        String requestBody = om.writeValueAsString(loginReqDto);

        //when
        ResultActions resultActions = mvc.perform(post("/auth/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        String jwtToken = resultActions.andReturn().getResponse().getHeader(JwtVo.HEADER);

        //then
        resultActions.andExpect(status().isUnauthorized());
    }

}