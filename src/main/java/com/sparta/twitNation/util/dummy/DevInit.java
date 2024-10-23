package com.sparta.twitNation.util.dummy;

import com.sparta.twitNation.domain.bookmark.BookmarkRepository;
import com.sparta.twitNation.domain.comment.Comment;
import com.sparta.twitNation.domain.comment.CommentRepository;
import com.sparta.twitNation.domain.like.Like;
import com.sparta.twitNation.domain.like.LikeRepository;
import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.post.PostRepository;
import com.sparta.twitNation.domain.retweet.Retweet;
import com.sparta.twitNation.domain.retweet.RetweetRepository;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.domain.user.UserRepository;
import com.sparta.twitNation.util.dummy.DummyObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DevInit extends DummyObject{

    @Profile("dev")
    @Bean
    CommandLineRunner init(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                           PostRepository postRepository, CommentRepository commentRepository,
                           BookmarkRepository bookmarkRepository, RetweetRepository retweetRepository, LikeRepository likeRepository){
        return (args) -> {
            User user = newUser();
            userRepository.save(user);

            Post post = newPost(user);
            postRepository.save(post);

            List<Comment> commentList = new ArrayList<>();
            List<Like> likeList = new ArrayList<>();
            List<Retweet> retweetList = new ArrayList<>();
            for(int i = 0;i<10;i++){
                commentList.add(newComment(post,user));
                likeList.add(newLike(post, user));
                retweetList.add(newRetweet(post, user));
            }

            commentRepository.saveAll(commentList);
            likeRepository.saveAll(likeList);
            retweetRepository.saveAll(retweetList);

        };
    }
}
