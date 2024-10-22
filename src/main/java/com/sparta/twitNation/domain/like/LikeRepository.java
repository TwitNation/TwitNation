package com.sparta.twitNation.domain.like;


import com.sparta.twitNation.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    @Modifying
    @Query("delete from Like l where l.post.id = :postId")
    int deleteLikesByPostId(@Param(value = "postId") Long postId);

    @Query("SELECT COUNT(l) FROM Like l WHERE l.post = :post")
    int countByPost(@Param("post") final Post post);
}
