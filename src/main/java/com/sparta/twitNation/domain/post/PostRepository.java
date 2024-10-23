package com.sparta.twitNation.domain.post;

import com.sparta.twitNation.domain.post.dto.PostWithDetails;
import com.sparta.twitNation.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByUser(final User user, final Pageable pageable);

    @Query("select p.id as postId, u.nickname as userNickname, u.profileImg as userProfileImg, " +
            "p.content as content, p.lastModifiedAt as modifiedAt, " +
            "count(distinct l.id) as likeCount, count(distinct c.id) as commentCount, count(distinct r.id) as retweetCount " +
            "from Post p " +
            "left join p.user u " +
            "left join Like l on l.post.id = p.id " +
            "left join Comment c on c.post.id = p.id " +
            "left join Retweet r on r.post.id = p.id " +
            "group by p.id, u.nickname, u.profileImg, p.content, p.lastModifiedAt " +
            "order by p.lastModifiedAt desc")
    Page<PostWithDetails> findAllWithDetails(Pageable pageable);
}
