package com.grampus.commnuity.dto;

import com.grampus.commnuity.domain.Category;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class BoardEditDto {
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

    private List<MultipartFile> newFiles; // 수정 시, 추가된 파일
    private List<Long> oldFiles; // 수정 시, 기존에 있던 파일
}