package com.sparta.twitNation.controller;

import com.sparta.twitNation.domain.bookmark.BookmarkRepository;
import com.sparta.twitNation.dto.bookmark.resp.BookmarkCreateRespDto;
import com.sparta.twitNation.service.BookmarkService;
import com.sparta.twitNation.util.api.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        //BookmarkCreateRespDto response = bookmarkService.createBookmark(postId);
        return new ResponseEntity<>(ApiResult.success(bookmarkService.createBookmark(postId)), HttpStatus.CREATED);
    }

//    @GetMapping("/bookmarks")
//    public ResponseEntity<ApiResult<List<BookmarkCreateRespDto>>> getAllBookmarks(@PathVariable(name = "userId")Long userId) {
//        return new ResponseEntity<>(ApiResult.success(bookmarkService.getAllBookmarks(userId)), HttpStatus.OK);
//    }
}
