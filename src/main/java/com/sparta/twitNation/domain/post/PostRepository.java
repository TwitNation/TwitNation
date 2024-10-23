package com.sparta.twitNation.domain.post;

import com.sparta.twitNation.domain.post.dto.PostDetailWithUser;
import com.sparta.twitNation.domain.user.User;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository{

    Page<Post> findByUser(final User user, final Pageable pageable);

    @Query("select p.id as postId, u.id as userId, u.nickname as nickname, " +
            "p.content as content, p.lastModifiedAt as modifiedAt, u.profileImg as profileImg " +
            "from Post p " +
            "left join User u on u = p.user " +
            "where p = :post")
    PostDetailWithUser getPostDetailWithUser(@Param("post") Post post);
}
