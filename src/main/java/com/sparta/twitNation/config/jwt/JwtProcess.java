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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
                .sign(Algorithm.HMAC512(JwtVo.SECRET));
    }

    //토큰 검증
    public LoginUser verify(String token){

        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtVo.SECRET)).build().verify(token);
            Long id = decodedJWT.getClaim("id").asLong();
            User user = User.builder().id(id).build();
            return new LoginUser(user);
        }catch (TokenExpiredException e){
            log.error("토큰이 만료되어 더 이상 유효하지 않습니다", e);
            throw e;
        } catch(SignatureVerificationException e){
            log.error("유효하지 않은 토큰 서명입니다", e);
            throw e;
        } catch (JWTDecodeException e) {
            log.error("유효하지 않은 JWT 형식입니다", e);
            throw e;
        }
        catch(JWTVerificationException e){
            log.error("JWT 검증 중 오류가 발생했습니다", e);
            throw e;
        }
    }
}
