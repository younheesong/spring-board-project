package com.grampus.commnuity.controller;

import com.grampus.commnuity.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;

@Controller
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadFile(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:"+fileService.getFullPath(filename));
    }

}
