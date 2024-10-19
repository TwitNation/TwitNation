package com.sparta.twitNation.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class JwtProcess {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    //토큰 생성
    public static String create(LoginUser loginUser){
        return JwtVo.TOKEN_PREFIX + JWT.create()
                .withSubject("twit-nation")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtVo.EXPIRATION_TIME))
                .withClaim("id", loginUser.getUser().getId()) //추후에 role 관련 정해지면 추가할 것
                .sign(Algorithm.HMAC512(JwtVo.SECRET));
    }

    //토큰 검증
    public static LoginUser verify(String token){
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtVo.SECRET)).build().verify(token);
        Long id = decodedJWT.getClaim("id").asLong();

        User user = User.builder().id(id).build();
        return new LoginUser(user);
    }
}
