package com.sparta.twitNation.domain.retweet;

import com.sparta.twitNation.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RetweetRepository extends JpaRepository<Retweet, Long> {

    @Modifying
    @Query("delete from Retweet r where r.post.id = :postId")
    int deleteRetweetsByPostId(@Param(value = "postId") Long postId);

    @Query("SELECT COUNT(l) FROM Like l WHERE l.post = :post")
    int countByPost(@Param("post") final Post post);

}
