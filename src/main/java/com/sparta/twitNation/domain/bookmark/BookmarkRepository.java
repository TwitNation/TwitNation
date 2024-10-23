package com.sparta.twitNation.domain.bookmark;

import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.dto.bookmark.req.BookmarkPostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {


    Optional<Bookmark> findByPostIdAndUserId(Long postId, Long userId);

    Page<Bookmark> findByUserId(Long userId, PageRequest pageRequest);

    @Modifying
    @Query("delete from Bookmark b where b.post.id = :postId")
    int deleteBookmarksByPostId(@Param(value = "postId") Long postId);


}
