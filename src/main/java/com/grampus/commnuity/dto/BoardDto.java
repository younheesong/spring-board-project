package com.grampus.commnuity.dto;

import com.grampus.commnuity.domain.Board;
import com.grampus.commnuity.domain.Category;
import com.grampus.commnuity.domain.File;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class BoardDto {
    private Long id;
    private String userLoginId;
    private String username;
    private Category category;
    private String title;
    private String content;
    private Integer likesCount;
    private Integer viewsCount;
    private LocalDateTime creationDate;
    private LocalDateTime modifiedDate;
    private List<File> files;
    private List<MultipartFile> newFiles;
    private List<Long> oldFiles;


    // entity -> dto
    public static BoardDto of(Board board){
        return BoardDto.builder()
                .id(board.getId())
                .userLoginId(board.getUser().getLoginId())
                .username(board.getUser().getUsername())
                .category(board.getCategory())
                .title(board.getTitle())
                .content(board.getContent())
                .likesCount(board.getLikesCount())
                .viewsCount(board.getViewsCount())
                .creationDate(board.getCreationDate())
                .modifiedDate(board.getModifiedDate())
                .files(board.getFiles())
                .build();
    }
}
