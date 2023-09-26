package com.grampus.commnuity.dto;

import com.grampus.commnuity.domain.Board;
import com.grampus.commnuity.domain.Category;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BoardCreateDto {
    private String category;
    private String title;
    private String content;
    private String contentImages;
    private List<MultipartFile> files;


    // dto -> entity
    public Board toEntity(long userId){


        return Board.builder()
                .userId(userId)
                .category(Category.of(category))
                .creationDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .title(title)
                .content(content)
                .contentImages(contentImages)
                .likesCount(0)
                .viewsCount(0)
                .build();

    }

}
