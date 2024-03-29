package com.grampus.commnuity.dto;

import com.grampus.commnuity.domain.Category;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BoardDto {
    private Long id;
    private String userLoginId;
    private String username;
    private Category category;
    private String title;
    private String content;
    private String contentImages;
    private Integer likesCount;
    private Integer viewsCount;
    private LocalDateTime creationDate;
    private LocalDateTime modifiedDate;
}
