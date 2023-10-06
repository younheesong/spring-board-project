package com.grampus.commnuity.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {
    private Long id;
    private String comment;
    private String username;
    private Long userId;
    private Long boardId;
    private LocalDateTime creationDate;
    private LocalDateTime modifiedDate;
    private Long parentCommentId;
    private boolean isDeleted;
    private boolean isModified;
}
