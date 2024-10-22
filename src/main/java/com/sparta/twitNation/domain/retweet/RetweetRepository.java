package com.sparta.twitNation.domain.retweet;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RetweetRepository extends JpaRepository<Retweet, Long> {
}
