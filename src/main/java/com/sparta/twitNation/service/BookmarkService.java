package com.sparta.twitNation.service;

import com.sparta.twitNation.domain.bookmark.Bookmark;
import com.sparta.twitNation.domain.bookmark.BookmarkRepository;
import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.post.PostRepository;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.domain.user.UserRepository;
import com.sparta.twitNation.dto.bookmark.req.BookmarkCreateReqDto;
import com.sparta.twitNation.dto.bookmark.resp.BookmarkCreateRespDto;
import com.sparta.twitNation.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;



    @Transactional
    public BookmarkCreateRespDto createBookmark(Long postId) {

        //게시글 존재 여부
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomApiException("존재하지 않는 게시글입니다.", 404));



        Optional<Bookmark> existingBookmark = bookmarkRepository.findByPostId(postId);
        boolean isBookmarked;

        if (existingBookmark.isPresent()) {
            bookmarkRepository.delete(existingBookmark.get());
            isBookmarked = false;
        } else {
            Bookmark bookmark = new Bookmark(post, true); // 수정된 생성자 사용
            bookmarkRepository.save(bookmark);
            isBookmarked = true;
        }

        return new BookmarkCreateRespDto(postId, isBookmarked);
    }
}
