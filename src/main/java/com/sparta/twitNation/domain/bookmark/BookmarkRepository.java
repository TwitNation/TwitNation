package com.sparta.twitNation.domain.bookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    @Modifying
    @Query("delete from Bookmark b where b.post.id = :postId")
    int deleteBookmarksByPostId(@Param(value = "postId") Long postId);
}