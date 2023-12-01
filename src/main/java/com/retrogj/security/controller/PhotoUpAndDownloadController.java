package com.retrogj.security.controller;

import com.retrogj.security.dto.PhotoUploadResponse;
import com.retrogj.security.service.PhotoStorageService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
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

        return new PhotoUploadResponse(fileName, contentType, url);
    }

    @GetMapping("/download/{fileName}")
    @Transactional
    public ResponseEntity<Resource> downloadPhoto(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = photoStorageService.downloadPhoto(fileName);

        String mimeType;

        try {
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;fileName=" + resource.getFilename()).body(resource);
    }

}
