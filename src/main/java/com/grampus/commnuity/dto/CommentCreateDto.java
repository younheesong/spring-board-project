package com.grampus.commnuity.dto;

import com.grampus.commnuity.domain.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentCreateDto {
    private Long boardId;
    private Long parentCommentId;
    private String comment;

    public Comment toEntity(long userId){
        return Comment.builder()
                .userId(userId)
                .boardId(boardId)
                .parentCommentId(parentCommentId!=null ? parentCommentId : 0)
                .comment(comment)
                .creationDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .isDeleted(false)
                .isModified(false)
                .build();
    }

}
