package com.sparta.twitNation.domain.like;


import com.sparta.twitNation.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    @Modifying
    @Query("delete from Like l where l.post.id = :postId")
    int deleteLikesByPostId(@Param(value = "postId") Long postId);

    int countByPost(@Param("post") final Post post);

    @Query("select l from Like l where l.post.id = :postId and l.user.id = :userId")
    Optional<Like> findByPostIdAndUserId(@Param(value = "postId")Long postId, @Param(value = "userId")Long userId);

    void deleteByUserId(Long userId);

}
