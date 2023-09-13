package com.grampus.commnuity.repository;

import com.grampus.commnuity.domain.Board;
import com.grampus.commnuity.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findAllByCategory(Category category, PageRequest pageRequest); // 카테고리에 맞게 페이지 조회
    Page<Board> findAllByCategoryAndTitleContains(Category category, String title, PageRequest pageRequest); // 카테고리에 맞게, 타이틀이 포함된 검색 결과 페이지 조회

    List<Board> findAllByUserLoginId(String loginId); // 해당 유저가 작성한 글 조회

    @Modifying
    @Query("update Board b set b.viewsCount = b.viewsCount +1 where b.id = :id")
    int updateViewsCount(@Param("id")Long id);

}
