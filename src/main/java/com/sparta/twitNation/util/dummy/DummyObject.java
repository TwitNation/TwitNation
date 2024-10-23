package com.sparta.twitNation.util.dummy;

import com.sparta.twitNation.domain.bookmark.Bookmark;
import com.sparta.twitNation.domain.comment.Comment;
import com.sparta.twitNation.domain.like.Like;
import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.retweet.Retweet;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.domain.user.UserRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class DummyObject {

    protected Like newLike(Post post, User user){
        return Like.builder()
                .post(post)
                .user(user)
                .build();
    }

    protected Comment newComment(Post post, User user){
        return Comment.builder()
                .post(post)
                .content("test comment")
                .user(user)
                .build();
    }

    protected Retweet mockRetweet(Post post){
        return Retweet.builder()
                .post(post)
                .user(null)
                .build();
    }
    protected Like mockLike(Post post){
        return Like.builder()
                .post(post)
                .user(null)
                .build();
    }

    protected Bookmark mockBookmark(Post post){
        return Bookmark.builder()
                .post(post)
                .user(null)
                .build();
    }

    protected Comment mockComment(Post post){
        return Comment.builder()
                .post(post)
                .content("test comment")
                .user(null)
                .build();
    }

    protected User newUser(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = "password1234";
        return User.builder()
                .id(1L)
                .nickname("userAAA")
                .role(UserRole.USER)
                .email("userA@email.com")
                .password(passwordEncoder.encode(password))
                .build();


    }
    protected Post newPost(User user){
        return Post.builder()
                .id(1L)
                .user(user)
                .content("test content")
                .build();
    }
}
