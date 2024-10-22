package com.sparta.twitNation.domain.like;

import com.sparta.twitNation.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("SELECT COUNT(l) FROM Like l WHERE l.post = :post")
    int countByPost(@Param("post") final Post post);
}
