package com.grampus.commnuity.domain;

import com.grampus.commnuity.dto.BoardDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @NotNull
    private User user; // 작성자

    @NotNull
    private LocalDateTime creationDate; // 생성날짜

    @NotNull
    private LocalDateTime modifiedDate; // 마지막 수정 날짜

    @Enumerated(EnumType.STRING)
    @NotNull
    private Category category; // 카테고리

    @NotNull
    private String title; // 제목

    @NotNull
    @Column(length=1000)
    private String content; // 내용

    @OneToMany(mappedBy = "board")
    private List<Like> likes = new ArrayList<>(); // 좋아요 목록

    @NotNull
    private Integer likesCount; // 좋아요수

    @NotNull
    private Integer viewsCount; // 조회수

    @OneToMany(mappedBy = "board")
    private List<File> files;



    public void update(BoardDto boardDto){
        if(boardDto.getCategory()!=null){
            this.category = boardDto.getCategory();
        }
        this.title= boardDto.getTitle();
        this.content = boardDto.getContent();
        this.modifiedDate = LocalDateTime.now();
    }

    public void changeLikesCount(Integer likesCount){
        this.likesCount = likesCount;
    }




}
