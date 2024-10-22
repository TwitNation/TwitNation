package com.sparta.twitNation.domain.post;

import com.sparta.twitNation.domain.post.dto.PageDetailWithUser;
import com.sparta.twitNation.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByUser(final User user, final Pageable pageable);

    @Query("select new com.sparta.twitNation.domain.post.dto.PageDetailWithUser(p.id, u.id, u.nickname, p.content,  p.lastModifiedAt, u.profileImg) " +
            "from Post p " +
            "left join User u on u = p.user " +
            "where p = :post")
    PageDetailWithUser getPostDetailWithUser(@Param(value = "post")Post post);
}
