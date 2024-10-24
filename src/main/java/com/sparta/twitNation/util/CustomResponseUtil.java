package com.sparta.twitNation.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.twitNation.config.auth.dto.LoginRespDto;
import com.sparta.twitNation.config.jwt.JwtVo;
import com.sparta.twitNation.util.api.ApiResult;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class CustomResponseUtil {

    private static final Logger log = LoggerFactory.getLogger(CustomResponseUtil.class);

    public static void fail(HttpServletResponse response, String msg, HttpStatus httpStatus){
        try {

            ApiResult<Object> responseDto = ApiResult.error(httpStatus.value(), msg);
            String responseBody = CustomUtil.convertToJson(responseDto);

            response.setContentType("application/json; charset=utf-8");
            response.setStatus(httpStatus.value());
            response.getWriter().write(responseBody);
            response.getWriter().flush();
        }catch (Exception e){
            log.error("서버 파싱 에러");
        }
    }

    public static void success(HttpServletResponse response, Object dto){
        try {
            ApiResult<Object> apiResult = ApiResult.success(dto);
            String responseBody = CustomUtil.convertToJson(apiResult);

            response.setContentType("application/json; charset=utf-8");
            response.setStatus(200);
            response.getWriter().println(responseBody);

        }catch (Exception e){
            log.error("서버 파싱 에러");
        }
    }


}
