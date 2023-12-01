package com.retrogj.security.service;

import com.retrogj.security.model.Photo;
import com.retrogj.security.repository.PhotoRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PhotoDatabaseService {

    private final PhotoRepository photoRepository;

    public Photo addPhoto(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Photo photo = new Photo();
        photo.setFileName(fileName);
        photo.setData(file.getBytes());

        photoRepository.save(photo);
        return photo;
    }

    public Photo getPhoto(String fileName, HttpServletRequest request) throws Exception {

        Photo photo = photoRepository.findByFileName(fileName);


        return photo;
    }
}
