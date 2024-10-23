package com.sparta.twitNation.controller;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.dto.bookmark.resp.BookmarkCreateRespDto;
import com.sparta.twitNation.dto.bookmark.resp.BookmarkViewRespDto;
import com.sparta.twitNation.service.BookmarkService;
import com.sparta.twitNation.util.api.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @PostMapping("/bookmarks/{postId}")
    public ResponseEntity<ApiResult<BookmarkCreateRespDto>> createBookmark(@PathVariable(name = "postId") Long postId, @AuthenticationPrincipal LoginUser loginUser) {


        // Bookmark 생성
        BookmarkCreateRespDto response = bookmarkService.createBookmark(loginUser, postId);

        return new ResponseEntity<>(ApiResult.success(response), HttpStatus.CREATED);
        //return new ResponseEntity<>(ApiResult.success(bookmarkService.createBookmark(postId, userId)), HttpStatus.CREATED);
    }

    //북마크 조회
    @GetMapping("/bookmarks")
    public ResponseEntity<ApiResult<BookmarkViewRespDto>> getBookmarks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @AuthenticationPrincipal LoginUser loginUser
    ){
        BookmarkViewRespDto response = bookmarkService.getBookmarks(page, limit, loginUser);
        return ResponseEntity.ok(ApiResult.success(response));

    }


}
