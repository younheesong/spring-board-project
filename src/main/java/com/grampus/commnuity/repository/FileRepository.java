package com.grampus.commnuity.repository;


import com.grampus.commnuity.domain.File;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FileRepository {
    List<File> getFilesByBoardId(Long boardId);

    void saveFile(File file);

    void deleteFile(Long fileId);

}
