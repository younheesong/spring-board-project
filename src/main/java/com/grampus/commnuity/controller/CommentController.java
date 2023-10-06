package com.grampus.commnuity.controller;


import com.grampus.commnuity.config.auth.UserDetail;
import com.grampus.commnuity.dto.CommentCreateDto;
import com.grampus.commnuity.dto.CommentDto;
import com.grampus.commnuity.dto.CommentsDto;
import com.grampus.commnuity.service.CommentService;
import com.grampus.commnuity.util.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    /* 게시글 내 댓글 조회 */
    @GetMapping("/{boardId}")
    @ResponseBody
    public CommentsDto getComments(@PathVariable Long boardId, @RequestParam(required = false, defaultValue = "1") int page){
        log.info("BOARD_ID : {}", boardId);

        int totalCommentCount = commentService.getTotalComment(boardId);
        log.info("TOTAL_COMMENT_COUNT : {}", totalCommentCount);
        Pagination pagination = new Pagination(totalCommentCount, page);


        List<CommentDto> comments = commentService.getCommentList(boardId, pagination.getStartIdx(), pagination.getPageSize());
        log.info("COMMENTS : {}", comments);

        CommentsDto commentList = new CommentsDto();

        commentList.totalPage = pagination.getTotalPage();
        commentList.currentPage =  pagination.getPage();

        ArrayList<HashMap> commentsAndRecomments = new ArrayList<>();
        comments.stream().forEach(comment->{
            HashMap<String, Object> commentMap = new HashMap<>();
            List<CommentDto> reComments = commentService.getReCommentList(comment.getId());

            commentMap.put("comment", comment);
            commentMap.put("reComments", reComments);

            commentsAndRecomments.add(commentMap);
        });
        commentList.comments = commentsAndRecomments;

        log.info("result : {}", commentList);


        return commentList;
    }

    /* 댓글 등록 */
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> saveComment(@RequestBody CommentCreateDto commentCreateDto, Authentication auth){
        log.info("boardId: "+commentCreateDto.getBoardId());
        log.info("parentCommentId: "+commentCreateDto.getParentCommentId());
        log.info("comment: "+commentCreateDto.getComment());
        // user 조회
        if(auth == null){
            return new ResponseEntity<>("fail", HttpStatus.UNAUTHORIZED);
        }
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        Long userId = userDetail.getId();

        log.info("userId: "+userId);

        Long commentId = commentService.writeComment(commentCreateDto, userId);
        if(commentId == null){
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("success", HttpStatus.OK);
    }


    /* 댓글 수정 */
    @PostMapping("/{commentId}/update")
    @ResponseBody
    public ResponseEntity<String> updateComment(@PathVariable Long commentId,@RequestBody CommentCreateDto commentCreateDto, Authentication auth){
        log.info("comment: "+ commentCreateDto.getComment());
        //comment 조회
        CommentDto commentDto = commentService.getComment(commentId);
        if(commentDto ==null){
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }

        // user 조회
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        Long userId = userDetail.getId();

        // 댓글 작성자인지 확인
        if(userId != commentDto.getUserId()){
             return new ResponseEntity<>("fail", HttpStatus.FORBIDDEN);
        }

        commentService.updateComment(commentId, commentCreateDto.getComment());
        return new ResponseEntity<>("success", HttpStatus.OK);
    }


    /* 댓글 삭제 */
    @PostMapping("/{commentId}/delete")
    @ResponseBody
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, Authentication auth){
        // comment 조회
        CommentDto comment = commentService.getComment(commentId);
        if(comment ==null){
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }
        // user 조회
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        Long userId = userDetail.getId();

        // 댓글 작성자인지 확인
        if(userId != comment.getUserId()){
             return new ResponseEntity<>("fail", HttpStatus.FORBIDDEN);
        }

        commentService.deleteComment(commentId);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
