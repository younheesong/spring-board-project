package com.grampus.commnuity.service;


import com.grampus.commnuity.domain.Board;
import com.grampus.commnuity.domain.File;
import com.grampus.commnuity.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    @Value("${file.dir}")
    private String fileDir;

    private final FileRepository fileRepository;

//    private final String rootPath = System.getProperty("user.dir");
//    private final String fileDir = rootPath + "/src/main/resources/static/upload-files/";

    public String getFullPath(String filename){
        return fileDir +filename;
    }
    // 다중 파일 저장
    public List<File> saveFiles(List<MultipartFile> files, Board board){
        List<File> storeFileResults = new ArrayList<>();
        for (MultipartFile file: files){
            if(!file.isEmpty()){
                storeFileResults.add(saveFile(file, board));
            }
        }
        return storeFileResults;
    }
    // 단일 파일 저장
    public File saveFile(MultipartFile file, Board board){
        if(file.isEmpty()){
            return null;
        }

        String originalFilename = file.getOriginalFilename();
        String savedFilename = createStoreFilename(originalFilename);

        try{
            Path path = Paths.get(fileDir, savedFilename);
            Files.createFile(path);
            file.transferTo(path); // 파일 저장
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileRepository.save(File.builder()
                        .board(board)
                .originalFileName(originalFilename)
                .savedFileName(savedFilename)
                .build());
    }

    // 서버에 저장할 이름 생성
    private String createStoreFilename(String originalFilename){
        String ext = extractExt(originalFilename);
        return UUID.randomUUID() + "." + ext;
    }

    // 확장자 추출
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }



    // 이미지 삭제
    @Transactional
    public void deleteFile(File file) throws IOException {
        fileRepository.delete(file);
        Files.deleteIfExists(Paths.get(getFullPath(file.getSavedFileName())));
    }



}
