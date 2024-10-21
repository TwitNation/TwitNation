package com.sparta.twitNation.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.ex.CustomJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
class JwtAuthorizationFilterTest {
    @InjectMocks
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @Mock
    private JwtProcess jwtProcess;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private FilterChain filterChain;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        jwtAuthorizationFilter = new JwtAuthorizationFilter(authenticationManager, jwtProcess);
    }

    @Test
    void success_validToken_test() throws IOException, ServletException {
        // given
        String token = JwtVo.TOKEN_PREFIX + JWT.create()
                .withSubject("twit-nation")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtVo.EXPIRATION_TIME))
                .withClaim("id", 1L)
                .sign(Algorithm.HMAC512(JwtVo.SECRET));
        request.addHeader(JwtVo.HEADER, token);

        User mockUser = User.builder().id(1L).build();
        LoginUser loginUser = new LoginUser(mockUser);
        when(jwtProcess.verify(token.replace(JwtVo.TOKEN_PREFIX, ""))).thenReturn(loginUser);

        // when
        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);

        // then
        // SecurityContext에 인증 정보가 설정되었는지 확인
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).isEqualTo(loginUser);

        // filterChain 호출 여부 확인
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void fail_invalidToken_test() throws IOException, ServletException {
        //given
        String token = JwtVo.TOKEN_PREFIX+"유효하지 않은 토큰";
        request.addHeader(JwtVo.HEADER, token);
        when(jwtProcess.verify(token.replace(JwtVo.TOKEN_PREFIX, ""))).thenThrow(new CustomJwtException(401, "유효하지 않은 토큰"));

        //then
        // exception 발생했는지 확인
        try {
            jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);
        } catch (CustomJwtException e) {
            assertThat(e.getMessage()).contains("유효하지 않은 토큰");
        }

        // FilterChain이 호출되지 않았는지 확인
        verify(filterChain, times(0)).doFilter(request, response);
    }

    @Test
    void fail_headerIsMissing_test() throws IOException, ServletException {
        try {
            jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);
        } catch (CustomJwtException e) {
            assertThat(e.getMessage()).contains("유효하지 않거나 Authorization 헤더가 누락되었습니다");
        }
        verify(filterChain, times(0)).doFilter(request, response);
    }
}