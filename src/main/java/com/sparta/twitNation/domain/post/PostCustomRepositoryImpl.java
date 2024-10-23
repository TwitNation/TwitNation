package com.sparta.twitNation.domain.post;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.twitNation.domain.like.QLike;
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
    public Page<Post> searchByNicknameAndKeyword(
            final String sort,
            final String keyword,
            final LocalDateTime startModifiedAt,
            final LocalDateTime endModifiedAt,
            final Pageable pageable
    ) {
        final QPost post = QPost.post;
        final QLike like = QLike.like;

        final List<Post> posts = queryFactory.selectFrom(post)
                .leftJoin(like).on(like.post.eq(post))
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
                .fetchCount();

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
