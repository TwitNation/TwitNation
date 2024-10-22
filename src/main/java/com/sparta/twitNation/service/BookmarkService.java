package com.sparta.twitNation.service;

import com.sparta.twitNation.config.auth.LoginUser;
import com.sparta.twitNation.domain.bookmark.Bookmark;
import com.sparta.twitNation.domain.bookmark.BookmarkRepository;
import com.sparta.twitNation.domain.comment.CommentRepository;
import com.sparta.twitNation.domain.like.LikeRespository;
import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.post.PostRepository;
import com.sparta.twitNation.domain.retweet.RetweetRepository;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.domain.user.UserRepository;
import com.sparta.twitNation.dto.bookmark.req.BookmarkPostDto;
import com.sparta.twitNation.dto.bookmark.resp.BookmarkCreateRespDto;
import com.sparta.twitNation.dto.bookmark.resp.BookmarkViewRespDto;
import com.sparta.twitNation.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sparta.twitNation.ex.ErrorCode.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRespository likeRespository;
    private final RetweetRepository retweetRepository;
    private final CommentRepository commentRepository;




    @Transactional
    public BookmarkCreateRespDto createBookmark(Long postId) {

        User user = User.createTestUser();
        LoginUser loginUser = new LoginUser(user);

        //게시글 존재 여부
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomApiException(POST_NOT_FOUND));



        Optional<Bookmark> existingBookmark = bookmarkRepository.findByPostIdAndUserId(postId, loginUser.getUser().getId());

        if (existingBookmark.isPresent()) {
            bookmarkRepository.delete(existingBookmark.get());

        } else {
            Bookmark bookmark = new Bookmark(post, loginUser.getUser()); // 수정된 생성자 사용
            bookmarkRepository.save(bookmark);

        }

        return new BookmarkCreateRespDto(postId, existingBookmark.isEmpty());
    }



    public BookmarkViewRespDto getBookmarks(int page, int limit) {

        User user = User.createTestUser();
        LoginUser loginUser = new LoginUser(user);

        PageRequest pageRequest = PageRequest.of(page, limit);


        // 북마크 목록을 조회하고, 관련된 게시글 정보
        Page<Bookmark> bookmarks = bookmarkRepository.findByUserId(loginUser.getUser().getId(), pageRequest);


        List<BookmarkPostDto> posts = bookmarks.getContent().stream()
                .map(bookmark -> {
                    Post post = bookmark.getPost();


                    return new BookmarkPostDto(
                            post.getUser().getNickname(), // 닉네임
                            post.getUser().getProfileImg(), // 프로필 이미지
                            post.getContent(), // 게시글 내용
                            post.getLastModifiedAt() // 수정일
//                        post.getLikeCount(), // 좋아요 수
//                        post.getRetweetCount(), // 리트윗 수
//                        post.getCommentCount() // 댓글 수
                    );
                })
                .collect(Collectors.toList());

        return new BookmarkViewRespDto(
                posts,
                (int) bookmarks.getTotalElements(),
                bookmarks.getNumber(),
                bookmarks.hasNext() ? bookmarks.getNumber() + 1 : -1,
                bookmarks.hasNext(),
                bookmarks.getSize()
        );
    }
}
