package com.sparta.twitNation.domain.post;

import com.sparta.twitNation.domain.post.dto.PostWithDetails;
import com.sparta.twitNation.domain.post.dto.PostDetailWithUser;
import com.sparta.twitNation.domain.post.dto.PostWithUser;
import com.sparta.twitNation.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository{

    Page<Post> findByUser(final User user, final Pageable pageable);

    @Query("select u.id as userId, p.id as postId, u.nickname as userNickname, u.profileImg as userProfileImg, " +
            "p.content as content, p.lastModifiedAt as modifiedAt, " +
            "count(distinct l.id) as likeCount, count(distinct c.id) as commentCount, count(distinct r.id) as retweetCount " +
            "from Post p " +
            "left join p.user u " +
            "left join Like l on l.post.id = p.id " +
            "left join Comment c on c.post.id = p.id " +
            "left join Retweet r on r.post.id = p.id " +
            "where p.user in (select f.following from Follow f where f.follower = :loginUser) " +
            "or p.user = :loginUser " +
            "group by p.id, u.id, u.nickname, u.profileImg, p.content, p.lastModifiedAt " +
            "order by p.lastModifiedAt desc")
    Page<PostWithDetails> findAllByFollowedUsers(@Param("loginUser") final User loginUser, final Pageable pageable);

    @Query("select u.id as userId, p.id as postId, u.nickname as userNickname, u.profileImg as userProfileImg, " +
            "p.content as content, p.lastModifiedAt as modifiedAt, " +
            "count(distinct l.id) as likeCount, count(distinct c.id) as commentCount, count(distinct r.id) as retweetCount " +
            "from Post p " +
            "left join p.user u " +
            "left join Like l on l.post.id = p.id " +
            "left join Comment c on c.post.id = p.id " +
            "left join Retweet r on r.post.id = p.id " +
            "where p.user = :user " +
            "group by p.id, u.nickname, u.profileImg, p.content, p.lastModifiedAt " +
            "order by p.lastModifiedAt desc")
    Page<PostWithUser> findAllByUser(@Param("user") final User user, final Pageable pageable);


  
    @Query("select p.id as postId, u.id as userId, u.nickname as nickname, " +
            "p.content as content, p.lastModifiedAt as modifiedAt, u.profileImg as profileImg " +
            "from Post p " +
            "left join User u on u = p.user " +
            "where p = :post")
    PostDetailWithUser getPostDetailWithUser(@Param("post") Post post);

    void deleteByUserId(Long userId);

}
