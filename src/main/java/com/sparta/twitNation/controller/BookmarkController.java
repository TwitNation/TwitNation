package com.sparta.twitNation.controller;

import com.sparta.twitNation.domain.bookmark.BookmarkRepository;
import com.sparta.twitNation.dto.bookmark.resp.BookmarkCreateRespDto;
import com.sparta.twitNation.service.BookmarkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @PostMapping("/bookmarks/{postId}")
    public ResponseEntity<BookmarkCreateRespDto> createBookmark(@PathVariable(name = "postId") Long postId) {
        BookmarkCreateRespDto response = bookmarkService.createBookmark(postId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
