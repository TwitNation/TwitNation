package com.sparta.twitNation.dto.bookmark.req;

import com.sparta.twitNation.domain.post.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BookmarkPostDto {
    @Schema(description = "북마크 조회 결과를 가져옵니다")
    private String userNickname; // 게시글 작성자 닉네임
    private String userProfileImg; // 게시글 작성자 프로필 사진
    private String content; // 게시글 내용
    private LocalDateTime lastModifiedAt; // 게시글 수정일
    private int likeCount; // 게시글 좋아요 수
    private int retweetCount; // 게시글 리트윗 수
    private int commentCount; // 게시글 댓글 수

    public BookmarkPostDto(String userNickname, String userProfileImg, String content, LocalDateTime lastModifiedAt, int likeCount, int retweetCount, int commentCount) {
        this.userNickname = userNickname;
        this.userProfileImg = userProfileImg;
        this.content = content;
        this.lastModifiedAt = lastModifiedAt;
        this.retweetCount = retweetCount;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    public BookmarkPostDto(Post post){
        this.userNickname = post.getUser().getNickname();
        this.userProfileImg = post.getUser().getProfileImg();;
        this.content = post.getContent();
        this.lastModifiedAt = post.getLastModifiedAt();

    }







}
