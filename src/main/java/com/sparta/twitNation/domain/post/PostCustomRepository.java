package com.sparta.twitNation.domain.post;

import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostCustomRepository {

    Page<Post> searchByNicknameAndKeyword(
            final String sort,
            final String keyword,
            final LocalDateTime startModifiedAt,
            final LocalDateTime endModifiedAt,
            final Pageable pageable
    );
}
