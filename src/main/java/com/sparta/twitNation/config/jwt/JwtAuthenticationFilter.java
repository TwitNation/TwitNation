package com.sparta.twitNation.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.config.auth.dto.LoginReqDto;
import com.sparta.twitNation.config.auth.dto.LoginRespDto;
import com.sparta.twitNation.util.CustomUtil;
import com.sparta.twitNation.util.api.ApiResult;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        setFilterProcessesUrl("/auth/login");
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.debug("디버그: attemptAuthentication 호출");

        try{
            ObjectMapper om = new ObjectMapper();
            LoginReqDto loginReqDto = om.readValue(request.getInputStream(), LoginReqDto.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginReqDto.getUsername(), loginReqDto.getPassword()
            );
            return authenticationManager.authenticate(authenticationToken);
        }catch (Exception e){
            //unsuccessfulAuthentication 호출
            log.debug(e.getMessage());
            throw new InternalAuthenticationServiceException(e.getMessage(), e);
        }
    }

    //로그인 실패 시
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ApiResult<Object> apiResult = ApiResult.error(HttpStatus.UNAUTHORIZED.value(), "로그인 실패");
        String responseBody = CustomUtil.convertToJson(apiResult);

        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().println(responseBody);
    }

    //로그인 성공 시
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        LoginUser loginUser = (LoginUser) authResult.getPrincipal();
        String token = JwtProcess.create(loginUser);
        LoginRespDto loginRespDto = new LoginRespDto(loginUser.getUser());

        ApiResult<LoginRespDto> apiResult = ApiResult.success(loginRespDto);
        String responseBody = CustomUtil.convertToJson(apiResult);

        response.addHeader(JwtVo.HEADER, token);
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(200);
        response.getWriter().println(responseBody);
    }
}
