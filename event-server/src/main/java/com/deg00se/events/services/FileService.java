package com.deg00se.events.services;

import com.deg00se.events.domain.enums.StorageType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    String uploadFile(InputStream inputStream, String originalName, StorageType storageType) throws IOException;
    void deleteFile(String storedName) throws IOException;
    void validateFile(MultipartFile file);
}
