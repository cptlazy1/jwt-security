package com.retrogj.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class PhotoStorageService {

    private Path photoStoragePath;
    private final String photoStorageLocation;

    public PhotoStorageService(@Value("uploads") String photoStorageLocation) {
        photoStoragePath = Paths.get(photoStorageLocation).toAbsolutePath().normalize();
        this.photoStorageLocation = photoStorageLocation;

        try {
            Files.createDirectories(photoStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("Issue in creating directory");
        }
    }

    public String storePhoto(MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        Path filePath = Paths.get(photoStoragePath + File.separator + fileName);

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Issue in storing the file", e);
        }
        return fileName;
    }

    public Resource downloadPhoto(String fileName) {
        Path filePath = Paths.get(photoStorageLocation).toAbsolutePath().resolve(fileName);
        Resource resource;

        try {
            resource = new UrlResource(filePath.toUri());
    } catch (MalformedURLException e) {
        throw new RuntimeException("Issue in reading the file", e);
    }
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("The file doesn't exist or is not readable");
        }
    }


}
