package com.sparta.twitNation.controller;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.domain.bookmark.BookmarkRepository;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.dto.bookmark.resp.BookmarkCreateRespDto;
import com.sparta.twitNation.service.BookmarkService;
import com.sparta.twitNation.util.api.ApiResult;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @PostMapping("/bookmarks/{postId}")
    public ResponseEntity<ApiResult<BookmarkCreateRespDto>> createBookmark(@PathVariable(name = "postId") Long postId) {

        User user = User.createTestUser();
        LoginUser loginUser = new LoginUser(user);

        // Bookmark 생성
        BookmarkCreateRespDto response = bookmarkService.createBookmark(postId, loginUser);

        return new ResponseEntity<>(ApiResult.success(response), HttpStatus.CREATED);
        //return new ResponseEntity<>(ApiResult.success(bookmarkService.createBookmark(postId, userId)), HttpStatus.CREATED);
    }


}
