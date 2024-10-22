package com.sparta.twitNation.util.dummy;

import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.user.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class DummyObject {

    protected User newUser(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = "password1234";
        return User.builder()
                .id(1L)
                .nickname("userAAA")
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
