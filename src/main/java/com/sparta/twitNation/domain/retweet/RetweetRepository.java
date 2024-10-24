package com.sparta.twitNation.domain.retweet;

import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository

public interface RetweetRepository extends JpaRepository<Retweet, Long> {

    @Modifying
    @Query("delete from Retweet r where r.post.id = :postId")
    int deleteRetweetsByPostId(@Param(value = "postId") Long postId);

    int countByPost(@Param("post") final Post post);

    Optional<Retweet> findByPostAndUser(Post post, User user);

    void deleteByUserId(Long userId);

}
