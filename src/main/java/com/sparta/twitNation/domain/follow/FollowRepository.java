package com.sparta.twitNation.domain.follow;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    void deleteByFollowerId(Long userId);

}
