package com.sparta.twitNation.domain.post;

import com.sparta.twitNation.dto.post.resp.PostsSearchRespDto;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostCustomRepository {

    Page<PostsSearchRespDto> searchByNicknameAndKeyword(
            final String sort,
            final String keyword,
            final LocalDateTime startModifiedAt,
            final LocalDateTime endModifiedAt,
            final Pageable pageable
    );
}
