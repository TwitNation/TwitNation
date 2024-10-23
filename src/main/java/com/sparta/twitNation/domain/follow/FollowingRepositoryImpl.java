package com.sparta.twitNation.domain.follow;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.twitNation.domain.user.QUser;
import com.sparta.twitNation.dto.follow.resp.FollowingReadPageRespDto;
import com.sparta.twitNation.dto.follow.resp.FollowingReadRespDto;
import com.sparta.twitNation.dto.follow.resp.QFollowingReadRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparta.twitNation.domain.follow.QFollow.*;
import static com.sparta.twitNation.domain.user.QUser.*;

@Repository
@RequiredArgsConstructor
public class FollowingRepositoryImpl implements FollowingRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public FollowingReadPageRespDto findAllFollowing(Long userId, Pageable pageable) {

        List<FollowingReadRespDto> followingList = jpaQueryFactory.select(new QFollowingReadRespDto(user.id, user.nickname, user.profileImg))
                .from(follow)
                .join(follow.following, user).fetchJoin()
                .where(follow.follower.id.eq(userId))
                .orderBy(follow.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = jpaQueryFactory.select(follow.count())
                .from(follow)
                .join(follow.following, user)
                .where(follow.follower.id.eq(userId))
                .fetchOne();

        PageImpl<FollowingReadRespDto> respDtos = new PageImpl<>(followingList, pageable, totalCount);
        return new FollowingReadPageRespDto(respDtos);
    }
}
