package com.grampus.commnuity.repository;

import com.grampus.commnuity.domain.Board;
import com.grampus.commnuity.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    /*카테고리 별 페이지 조회*/
    Page<Board> findAllByCategory(Category category, PageRequest pageRequest);

    /* 카테고리 별 타이틀이 포함된 검색 결과 페이지 조회*/
    Page<Board> findAllByCategoryAndTitleContains(Category category, String title, PageRequest pageRequest);

    /* 해당 유저가 작성한 글 조회 */
    Page<Board> findAllByUserLoginId(String loginId, PageRequest pageRequest);

    /* 조회수 증가 */
    @Modifying
    @Query("update Board b set b.viewsCount = b.viewsCount +1 where b.id = :id")
    int updateViewsCount(@Param("id")Long id);

}
