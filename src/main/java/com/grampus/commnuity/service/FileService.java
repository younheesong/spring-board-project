package com.grampus.commnuity.service;


import com.grampus.commnuity.domain.File;
import com.grampus.commnuity.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {
    @Value("${file.dir}")
    private String fileDir;

    private final FileRepository fileRepository;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    // 파일 가져오기
    public List<File> getFiles(long boardId) {
        List<File> files = fileRepository.getFilesByBoardId(boardId);
        return files;
    }

    // 다중 파일 저장
    public void saveFiles(List<MultipartFile> files, Long boardId) {
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                saveFile(file, boardId);
            }
        }
    }

    // 단일 파일 저장
    @Transactional
    public void saveFile(MultipartFile file, Long boardId) {
        if (file.isEmpty()) {
            return;
        }

        String originalFilename = file.getOriginalFilename();
        String savedFilename = createStoreFilename(originalFilename);

        createFilePath(file, savedFilename);

        fileRepository.saveFile(File.builder()
                .boardId(boardId)
                .savedFileName(savedFilename)
                .originalFileName(originalFilename)
                .build());

    }

    public void createFilePath(MultipartFile file, String savedFilename){
        try {
            Path path = Paths.get(fileDir, savedFilename);
            Files.createFile(path);
            file.transferTo(path); // 파일 저장
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 서버에 저장할 이름 생성
    public String createStoreFilename(String originalFilename) {
        String ext = extractExt(originalFilename);
        return UUID.randomUUID() + "." + ext;
    }

    // 확장자 추출
    public String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }


    // 이미지 삭제
    @Transactional
    public void deleteFiles(List<File> files) {
        for (File file : files) {
            deleteFile(file);
        }
    }

    @Transactional
    public void deleteFile(@NotNull File file) {
        log.info("DELETE_FILE_ID : "+file.getId());
        fileRepository.deleteFile(file.getId());
        try {
            Files.deleteIfExists(Paths.get(getFullPath(file.getSavedFileName())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFile(@NotNull String savedFilename) {
        log.info("DELETE_SAVED_FILE_NAME : "+getFullPath(savedFilename));
        try {
            Files.deleteIfExists(Paths.get(getFullPath(savedFilename)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
