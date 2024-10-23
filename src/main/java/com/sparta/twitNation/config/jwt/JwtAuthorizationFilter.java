package com.sparta.twitNation.config.jwt;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.ex.CustomApiException;
import com.sparta.twitNation.ex.CustomJwtException;
import com.sparta.twitNation.ex.ErrorCode;
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
        try {
            if(request.getRequestURI().contains("/swagger") ||
                    request.getRequestURI().contains("/api-docs") ||
                    request.getRequestURI().contains("auth")) {
                chain.doFilter(request, response);
                return;
            }

            if (!isAuthorizationHeaderValid(request)) {
                throw new CustomApiException(ErrorCode.TOKEN_MISSING);
            }

            log.debug("디버그: 토큰 존재");
            String token = request.getHeader(JwtVo.HEADER).replace(JwtVo.TOKEN_PREFIX, ""); //Bearer 제거

            LoginUser loginUser = jwtProcess.verify(token);
            log.debug("디버그: 토큰 검증 완료");

            Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("시큐리티 컨텍스트: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());

            chain.doFilter(request, response); //다시 체인
        } catch (CustomJwtException  e) {
            log.warn("JWT 검증 실패: {}", e.getMessage());
            throw e;
        } catch(CustomApiException e){
            log.warn("헤더 누락: {}", e.getMessage());
            throw e;
        }
    }

    private boolean isAuthorizationHeaderValid(HttpServletRequest request) {
        String header = request.getHeader(JwtVo.HEADER);
        return header != null && header.startsWith(JwtVo.TOKEN_PREFIX);
    }

}
