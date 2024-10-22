package com.sparta.twitNation.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.ex.CustomJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


class JwtProcessTest {
    @InjectMocks
    private JwtProcess jwtProcess;
    private LoginUser mockLoginUser;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        User user = User.builder().id(1L).build();
        mockLoginUser = new LoginUser(user);
    }

    @Test
    void createToken_test() {
        // when
        String token = jwtProcess.create(mockLoginUser);

        // then
        assertThat(token).isNotNull();
        assertThat(token).startsWith(JwtVo.TOKEN_PREFIX);
    }

    @Test
    void verify_validToken_test(){
        String token = jwtProcess.create(mockLoginUser);

        // when
        LoginUser verifiedUser = jwtProcess.verify(token.replace(JwtVo.TOKEN_PREFIX, ""));

        // then
        assertThat(verifiedUser).isNotNull();
        assertThat(verifiedUser.getUser().getId()).isEqualTo(mockLoginUser.getUser().getId());
    }

    @Test
    void verify_expiredToken_test() {

        String expiredToken = JwtVo.TOKEN_PREFIX + JWT.create()
                .withSubject("twit-nation")
                .withExpiresAt(new Date(System.currentTimeMillis() - 1000))
                .withClaim("id", mockLoginUser.getUser().getId())
                .sign(Algorithm.HMAC512(JwtVo.SECRET));

        // then
        assertThatThrownBy(() -> jwtProcess.verify(expiredToken.replace(JwtVo.TOKEN_PREFIX, "")))
                .isInstanceOf(CustomJwtException.class)
                .hasMessageContaining("만료된 토큰입니다")
                .extracting("status").isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void verify_invalidSignature_test() {
        String invalidToken = JwtVo.TOKEN_PREFIX + JWT.create()
                .withSubject("twit-nation")
                .withClaim("id", mockLoginUser.getUser().getId())
                .sign(Algorithm.HMAC512("잘못된 시크릿 키"));

        //then
        assertThatThrownBy(() -> jwtProcess.verify(invalidToken.replace(JwtVo.TOKEN_PREFIX, "")))
                .isInstanceOf(CustomJwtException.class)
                .hasMessageContaining("유효하지 않은 토큰 서명 입니다");
    }


}