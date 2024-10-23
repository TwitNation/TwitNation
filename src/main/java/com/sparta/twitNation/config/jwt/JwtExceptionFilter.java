package com.sparta.twitNation.config.jwt;

import com.sparta.twitNation.ex.CustomApiException;
import com.sparta.twitNation.ex.CustomJwtException;
import com.sparta.twitNation.util.CustomUtil;
import com.sparta.twitNation.util.api.ApiResult;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        }catch (CustomJwtException e){
            setErrorResponse(e.getStatus(), response, e.getMessage());
        }catch(CustomApiException e){
            setErrorResponse(e.getErrorCode().getStatus(), response, e.getMessage());
        }
    }
    private void setErrorResponse(int status, HttpServletResponse response, String message) throws IOException {
        ApiResult<Object> jwtExceptionResponse = ApiResult.error(status, message);
        response.setStatus(status);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(CustomUtil.convertToJson(jwtExceptionResponse));
    }
}