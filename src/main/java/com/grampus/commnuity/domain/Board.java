package com.grampus.commnuity.domain;

import com.grampus.commnuity.dto.BoardDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id @GeneratedValue
    @Column(name="board_id")
    private Long id; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user; // 작성자

    private LocalDateTime creationDate; // 생성날짜
    private LocalDateTime modifiedDate; // 마지막 수정 날짜

    @Enumerated(EnumType.STRING)
    private Category category; // 카테고리

    private String title; // 제목
    private String content; // 내용

    @OneToMany(mappedBy = "board")
    private List<Like> likes; // 좋아요 목록
    private Integer likesCount; // 좋아요수
    private Integer viewsCount; // 조회수

    public void update(BoardDto boardDto){
        this.category = boardDto.getCategory();
        this.title= boardDto.getTitle();
        this.content = boardDto.getContent();
    }

    public void changeLikesCount(Integer likesCount){
        this.likesCount = likesCount;
    }

    public void changeViewsCount(Integer viewsCount){
        this.viewsCount = viewsCount;
    }



}
