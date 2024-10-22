package com.sparta.twitNation.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.domain.user.UserRole;
import com.sparta.twitNation.ex.CustomJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProcess {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    //토큰 생성
    public String create(LoginUser loginUser){
        return JwtVo.TOKEN_PREFIX + JWT.create()
                .withSubject("twit-nation")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtVo.EXPIRATION_TIME))
                .withClaim("id", loginUser.getUser().getId()) //추후에 role 관련 정해지면 추가할 것
                .withClaim("role",loginUser.getUser().getRole().toString())
                .sign(Algorithm.HMAC512(JwtVo.SECRET));
    }

    //토큰 검증
    public LoginUser verify(String token){

        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtVo.SECRET)).build().verify(token);

            Long id = decodedJWT.getClaim("id").asLong();
            String role = decodedJWT.getClaim("role").asString();

            User user = User.builder().id(id).role(UserRole.valueOf(role)).build();
            return new LoginUser(user);
        }catch (TokenExpiredException e){
            log.error("토큰 만료: {}", e.getMessage(), e);
            throw new CustomJwtException(HttpStatus.UNAUTHORIZED.value(), "만료된 토큰입니다");
        } catch(SignatureVerificationException e){
            log.error("JWT 서명 오류: {}", e.getMessage(), e);
            throw new CustomJwtException(HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 토큰 서명 입니다");
        } catch (JWTDecodeException e) {
            log.error("JWT 형식 오류: {}", e.getMessage(), e);
            throw new CustomJwtException(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 JWT 형식입니다");
        } catch(JWTVerificationException e){
            log.error("JWT 검증 실패: 잘못된 서명 또는 기타 오류 {}", e.getMessage(), e);
            throw new CustomJwtException(HttpStatus.BAD_REQUEST.value(), "잘못된 JWT 토큰입니다");
        }
    }
}
