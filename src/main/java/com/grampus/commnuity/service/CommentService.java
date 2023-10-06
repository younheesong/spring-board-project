package com.grampus.commnuity.service;

import java.util.List;

import com.grampus.commnuity.domain.Comment;
import com.grampus.commnuity.dto.BoardDto;
import com.grampus.commnuity.dto.CommentCreateDto;
import com.grampus.commnuity.dto.CommentDto;
import com.grampus.commnuity.repository.BoardRepository;
import com.grampus.commnuity.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardService boardService;

    /* 게시글 내 댓글 조회(루트 댓글만, 리댓글 x) */
    public List<CommentDto> getCommentList(Long boardId, int start, int pageSize){
        return commentRepository.getCommentList(boardId, start, pageSize);
    }

    /* 게시글 내 댓글 수 조회(루트 댓글만, 리댓글 x) */
    public int getTotalComment(Long boardId){
        return commentRepository.getTotalComment(boardId);
    }

    /* 댓글에 대한 리댓글 조회 */
    public List<CommentDto> getReCommentList(Long boardId){
        return commentRepository.getReCommentList(boardId);
    }

    /* 단일 댓글 조회 */
    public CommentDto getComment (Long commentId) {
        return commentRepository.getComment(commentId);
    }


    /* 댓글 등록 */
    public Long writeComment(CommentCreateDto commentCreateDto, Long userId){
        // board가 있는지 확인
        BoardDto board = boardService.getBoard(commentCreateDto.getBoardId());
        if (board == null) {
            return null;
        }

        Comment comment = commentCreateDto.toEntity(userId);
        log.info("comment : {}", comment);
        commentRepository.saveComment(comment);

        return comment.getId();
    }

    /* 댓글 수정 */
    public void updateComment(Long commentId, String comment){
        commentRepository.updateComment(commentId, comment);
    }

    /*댓글 삭제 */
    public void deleteComment(Long commentId){
        commentRepository.deleteComment(commentId);
    }



}
