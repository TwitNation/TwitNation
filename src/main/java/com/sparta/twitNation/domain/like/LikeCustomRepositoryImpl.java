package com.sparta.twitNation.domain.like;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.twitNation.domain.post.Post;
import com.sparta.twitNation.domain.user.User;
import com.sparta.twitNation.dto.like.resp.LikeReadPageRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparta.twitNation.domain.comment.QComment.comment;
import static com.sparta.twitNation.domain.like.QLike.like;
import static com.sparta.twitNation.domain.post.QPost.post;
import static com.sparta.twitNation.domain.retweet.QRetweet.retweet;
import static com.sparta.twitNation.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class LikeCustomRepositoryImpl implements LikeCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<LikeReadPageRespDto> searchLikes(Long userId, Pageable pageable) {
        List<Like> likeList = jpaQueryFactory
                .select(like)
                .from(like)
                .join(like.post, post).fetchJoin()
                .join(post.user, user).fetchJoin()
                .where(like.user.id.eq(userId))
                .orderBy(like.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = jpaQueryFactory.select(post.count())
                .from(like)
                .where(like.user.id.eq(userId))
                .fetchOne();

        List<LikeReadPageRespDto> likeReadPageRespDtos = likeList.stream()
                .map(like -> {
                    Post likePost = like.getPost();
                    User postUser = like.getUser();
                    Long likeCount = getLikeCount(likePost.getId());
                    Long retweetCount = getRetweetCount(likePost.getId());
                    Long commentCount = getCommentCount(likePost.getId());
                    return LikeReadPageRespDto.from(likePost, postUser, likeCount, commentCount, retweetCount);
                })
                .toList();

        return new PageImpl<>(likeReadPageRespDtos, pageable, totalCount);
    }

    private Long getLikeCount(Long postId) {
        return jpaQueryFactory
                .select(like.count())
                .from(like)
                .where(like.post.id.eq(postId))
                .fetchOne();
    }

    private Long getCommentCount(Long postId) {
        return jpaQueryFactory
                .select(comment.count())
                .from(comment)
                .where(comment.post.id.eq(postId))
                .fetchOne();
    }

    private Long getRetweetCount(Long postId) {
        return jpaQueryFactory
                .select(retweet.count())
                .from(retweet)
                .where(retweet.post.id.eq(postId))
                .fetchOne();
    }

}
