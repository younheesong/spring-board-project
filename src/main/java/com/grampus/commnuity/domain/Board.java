package com.grampus.commnuity.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    private Long id;

    private Long userId; // 작성자 id

    private LocalDateTime creationDate; // 생성날짜

    private LocalDateTime modifiedDate; // 마지막 수정 날짜

    private Category category; // 카테고리

    private String title; // 제목

    private String content; // 에디터 내용

    private String contentImages; // 에디터 내의 저장된 이미지 파일

    private Integer likesCount; // 좋아요수

    private Integer viewsCount; // 조회수

}
