package com.sparta.twitNation.domain.retweet;

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
}
