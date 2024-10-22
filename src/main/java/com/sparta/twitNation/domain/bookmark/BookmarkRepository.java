package com.sparta.twitNation.domain.bookmark;

import com.sparta.twitNation.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByPostId(Long postId);

    Optional<Bookmark> findByPostAndUserId(Post post, Long user_id);

    Optional<Bookmark> findByPostIdAndUserId(Long postId, Long userId);

}
