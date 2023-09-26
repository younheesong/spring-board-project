package com.grampus.commnuity.repository;

import com.grampus.commnuity.domain.Board;
import com.grampus.commnuity.domain.Category;
import com.grampus.commnuity.dto.BoardDto;
import com.grampus.commnuity.dto.BoardEditDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BoardRepository {
    /* board 리스트 조회 */
    List<BoardDto> getBoardList(@Param("category") Category category, @Param("keyword") String keyword, @Param("start") int start, @Param("pageSize") int pageSize);

    /* 자신이 작성한 board 리스트 조회 */
    List<BoardDto> getMyBoardList(@Param("userId") Long userId, @Param("start") int start, @Param("pageSize") int pageSize);

    /* board 리스트 total 수 조회 */
    int getTotalBoardCount(@Param("category") Category category, @Param("keyword") String keyword);

    /* 자신이 작성한 board total 수 조회 */
    int getTotalMyBoardCount(Long userId);

    /* 특정 글 조회 */
    BoardDto getBoardItemById(Long boardId);

    /* 글생성 */
    void saveBoard(Board board);

    /* 글 편집 */
    void updateBoard(BoardEditDto board);

    /* 글 삭제 */
    void deleteBoard(Long boardId);

    /* 조회수 업데이트 */
    int updateViewsCount(Long boardId);
    /*하트 수 증가*/
    void increaseLikesCount(Long boardId);

    void decreaseLikesCount(Long boardId);
}
