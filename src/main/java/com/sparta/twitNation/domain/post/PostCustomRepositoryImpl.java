package com.sparta.twitNation.domain.post;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.twitNation.domain.comment.QComment;
import com.sparta.twitNation.domain.like.QLike;
import com.sparta.twitNation.domain.retweet.QRetweet;
import com.sparta.twitNation.dto.post.resp.PostsSearchRespDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PostsSearchRespDto> searchByNicknameAndKeyword(
            final String sort,
            final String keyword,
            final LocalDateTime startModifiedAt,
            final LocalDateTime endModifiedAt,
            final Pageable pageable
    ) {
        final QPost post = QPost.post;
        final QLike like = QLike.like;
        final QComment comment = QComment.comment;
        final QRetweet retweet = QRetweet.retweet;

        final List<PostsSearchRespDto> posts = queryFactory
                .select(
                        Projections.constructor(
                                PostsSearchRespDto.class,
                                post.id,
                                post.user.id,
                                post.user.nickname,
                                post.user.profileImg,
                                post.content,
                                post.lastModifiedAt,
                                ExpressionUtils.as(
                                        like.id.countDistinct(), "likeCount"),
                                ExpressionUtils.as(
                                        comment.id.countDistinct(), "commentCount"),
                                ExpressionUtils.as(
                                        retweet.id.countDistinct(), "retweetCount")
                        )
                )
                .from(post)
                .leftJoin(like).on(like.post.eq(post))
                .leftJoin(comment).on(comment.post.eq(post))
                .leftJoin(retweet).on(retweet.post.eq(post))
                .where(
                        containsKeyword(keyword, post),
                        getDatePredicate(startModifiedAt, endModifiedAt, post)
                )
                .groupBy(post.id)
                .orderBy(getSortOrder(sort, post))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        final long total = queryFactory.selectFrom(post)
                .where(
                        containsKeyword(keyword, post),
                        getDatePredicate(startModifiedAt, endModifiedAt, post)
                )
                .fetch()
                .size();

        return new PageImpl<>(posts, pageable, total);
    }

    private BooleanExpression getDatePredicate(LocalDateTime startDate, LocalDateTime endDate, QPost post) {
        BooleanExpression predicate = post.isNotNull();

        if (startDate != null) {
            predicate = predicate.and(post.createdAt.goe(startDate));
        }
        if (endDate != null) {
            predicate = predicate.and(post.lastModifiedAt.loe(endDate));
        }
        return predicate;
    }

    private OrderSpecifier<?> getSortOrder(String sort, QPost post) {
        final QLike like = QLike.like;

        return switch (sort) {
            case "like" -> like.count().desc();
            default -> post.lastModifiedAt.desc();
        };
    }

    private BooleanExpression containsKeyword(String keyword, QPost post) {
        if (keyword == null || keyword.isEmpty()) {
            return null;
        }
        return post.user.nickname.contains(keyword).or(post.content.contains(keyword));
    }
}
