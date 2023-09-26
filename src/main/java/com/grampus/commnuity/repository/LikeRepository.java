package com.grampus.commnuity.repository;

import com.grampus.commnuity.domain.Like;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
@Repository
public interface LikeRepository {
    void saveLike(Like like);

    void deleteLike(@Param("userId") Long userId, @Param("boardId") Long boardId);

    Optional<Like> getLikeByUserLoginIdAndBoardId(@Param("userId") Long userId, @Param("boardId") Long boardId);
}
