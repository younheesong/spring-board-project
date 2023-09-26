package com.grampus.commnuity.controller;

import com.grampus.commnuity.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FileController {
    private final FileService fileService;

    @ResponseBody
    @PostMapping(value="/images/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap uploadImage(MultipartFile upload) {
        HashMap<Object, Object> result = new HashMap<>();

        log.info("get file " + upload);

        String originalFilename = upload.getOriginalFilename();
        String savedFilename = fileService.createStoreFilename(originalFilename);

        fileService.createFilePath(upload, savedFilename);

        result.put("uploaded", 1);
        result.put("url", "/images/" + savedFilename);
        result.put("fileName", originalFilename);
        log.info("result " + result);

        return result;
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String filename) throws IOException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.inline()
                .filename(filename, StandardCharsets.UTF_8)
                .build()
                .toString())
                .body(new UrlResource("file:" + fileService.getFullPath(filename)));
    }


    @GetMapping("/files/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws IOException {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                .filename(filename, StandardCharsets.UTF_8)
                .build()
                .toString())
                .body(new UrlResource("file:" + fileService.getFullPath(filename)));
    }

}
