package com.grampus.commnuity.domain;

import lombok.*;

import java.time.LocalDateTime;


@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    private Long id;

    private Long userId; // 작성자 id

    private Long boardId; // 댓글 단 게시글 id

    private Long parentCommentId; // 대댓글일 경우, 대댓글의 부모 comment id

    private String comment; // 댓글 내용

    private LocalDateTime creationDate; // 댓글 생성 시기

    private LocalDateTime modifiedDate; // 댓글 수정 시기

    private Boolean isDeleted; // 댓글 삭제 여부

    private Boolean isModified; // 댓글 수정 여부

}
