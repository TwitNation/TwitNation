package com.sparta.twitNation.domain.comment;


import com.sparta.twitNation.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Modifying
    @Query("delete from Comment c where c.post.id = :postId")
    int deleteCommentsByPostId(@Param(value = "postId") Long postId);


    int countByPost(@Param("post") final Post post);

    @EntityGraph(attributePaths = {"user"})
    Page<Comment> findAllByPost(Post post, Pageable pageable);

    void deleteByUserId(Long userId);

}
