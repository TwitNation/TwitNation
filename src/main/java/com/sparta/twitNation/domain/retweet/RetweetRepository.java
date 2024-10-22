package com.sparta.twitNation.domain.retweet;

import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RetweetRepository extends JpaRepository<Retweet, Long> {

    @Query("SELECT COUNT(l) FROM Like l WHERE l.post = :post")
    int countByPost(@Param("post") final Post post);

    Optional<Retweet> findByPostAndUser(Post post, User user);
}
