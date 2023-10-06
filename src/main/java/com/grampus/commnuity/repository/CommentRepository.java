package com.grampus.commnuity.repository;

import com.grampus.commnuity.domain.Comment;
import com.grampus.commnuity.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentRepository {
    /* 게시글 내 댓글 조회 */
    List<CommentDto> getCommentList(@Param("boardId") Long boardId, @Param("start") int start, @Param("pageSize") int pageSize);

    /* 게시글 내 댓글 수 조회 */
    int getTotalComment(Long boardId);

    /* 댓글에 대한 리댓글 조회 */
    List<CommentDto> getReCommentList(Long boardId);

    /* 단일 댓글 조회 */
    CommentDto getComment(Long commentId);

    /*댓글 혹은 리댓글 저장*/
    void saveComment(Comment comment);

    /*댓글 수정*/
    void updateComment(@Param("commentId") Long commentId, @Param("comment") String comment);

    /* 댓글 삭제 ) isDeleted: true 변경 */
    void deleteComment(Long commentId);

}
