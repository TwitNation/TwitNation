package com.sparta.twitNation.config.jwt;

import com.sparta.twitNation.config.auth.LoginUser;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final JwtProcess jwtProcess;
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtProcess jwtProcess) {
        super(authenticationManager);
        this.jwtProcess = jwtProcess;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(!isAuthorizationHeaderValid(request)){
            sendErrorMessage(response, "유효하지 않거나 Authorization 헤더가 누락되었습니다");
            return;
        }
        log.debug("디버그: 토큰 존재");
        String token = request.getHeader(JwtVo.HEADER).replace(JwtVo.TOKEN_PREFIX, ""); //Bearer 제거

        LoginUser loginUser = jwtProcess.verify(token);
        log.debug("디버그: 토큰 검증 완료");

        Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response); //다시 체인
    }

    private boolean isAuthorizationHeaderValid(HttpServletRequest request){
        String header = request.getHeader(JwtVo.HEADER);
        return header != null && header.startsWith(JwtVo.TOKEN_PREFIX);
    }

    private void sendErrorMessage(HttpServletResponse response, String message) throws IOException{
        log.warn("Authorization 헤더 오류: {}", message);

        ApiResult<Object> apiResult = ApiResult.error(HttpStatus.UNAUTHORIZED.value(), message);
        String responseBody = CustomUtil.convertToJson(apiResult);

        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().println(responseBody);
    }
}
