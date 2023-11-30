package com.retrogj.security.controller;

import com.retrogj.security.dto.PhotoUploadResponse;
import com.retrogj.security.service.PhotoStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PhotoUpAndDownloadController {

    private final PhotoStorageService photoStorageService;

    @PostMapping("/upload")
    public PhotoUploadResponse uploadPhoto(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = photoStorageService.storePhoto(file);
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(fileName)
                .toUriString();

        String contentType = file.getContentType();

        return new PhotoUploadResponse(fileName, url, contentType);
    }
}
