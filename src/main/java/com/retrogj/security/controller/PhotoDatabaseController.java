package com.retrogj.security.controller;

import com.retrogj.security.dto.PhotoUploadResponse;
import com.retrogj.security.model.Photo;
import com.retrogj.security.service.PhotoDatabaseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class PhotoDatabaseController {

    private final PhotoDatabaseService photoDatabaseService;

    @PostMapping("/upload-db")
    public PhotoUploadResponse uploadPhoto(@RequestParam("file") MultipartFile file) throws IOException {
        Photo photo = photoDatabaseService.addPhoto(file);
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download-db/")
                .path(Objects.requireNonNull(photo.getFileName()))
                .toUriString();

        String contentType = file.getContentType();

        return new PhotoUploadResponse(photo.getFileName(), contentType, url);
    }

    @GetMapping("/download-db/{fileName}")
    public ResponseEntity<byte[]> downloadPhoto(@PathVariable String fileName, HttpServletRequest request) throws Exception {
        Photo photo = photoDatabaseService.getPhoto(fileName, request);

        try {
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment;fileName=" + photo.getFileName()).body(photo.getData());
        } catch (Exception e) {
            throw new Exception("Issue in downloading the file", e);
        }
    }
}
