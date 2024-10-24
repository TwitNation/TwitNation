package com.sparta.twitNation.controller;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.dto.retweet.resp.RetweetToggleRespDto;
import com.sparta.twitNation.util.api.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "리트윗 API", description = "리트윗 관련 API ")
public interface RetweetControllerDocs {
    @Operation(summary = "리트윗 토글(리트윗하기/리트윗해제)", description = "한 번의 요청으로 리트윗 요청/해제에 대한 결과를 리턴합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다")
    })
    public ResponseEntity<ApiResult<RetweetToggleRespDto>> toggleRetweet(@PathVariable Long postId, @AuthenticationPrincipal LoginUser loginUser);
}
