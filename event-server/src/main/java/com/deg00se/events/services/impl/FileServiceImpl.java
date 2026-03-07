package com.deg00se.events.services.impl;

import com.deg00se.events.config.StorageProperties;
import com.deg00se.events.domain.enums.StorageType;
import com.deg00se.events.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private final StorageProperties storageProperties;
    private final Path rootPath;

    public FileServiceImpl(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
        this.rootPath = Paths.get(storageProperties.root());
    }

    @Override
    public String uploadFile(InputStream inputStream, String originalName, StorageType storageType) throws IOException {
        LocalDate today = LocalDate.now();
        Path storeDirectory = rootPath.resolve(storageTypeToPath(storageType));

        Files.createDirectories(storeDirectory);

        String ext = getFileExtension(originalName);
        String storageName = UUID.randomUUID() + (ext.isEmpty() ? "" : "." + ext);
        Path filePath = storeDirectory.resolve(storageName);

        try (OutputStream outputStream = Files.newOutputStream(filePath, StandardOpenOption.CREATE_NEW)) {
            StreamUtils.copy(inputStream, outputStream);
        }

        return  rootPath.relativize(filePath).toString();
    }

    @Override
    public void deleteFile(String storedName) throws IOException {
        Path storedFile = rootPath.resolve(storedName);
        boolean result = Files.deleteIfExists(storedFile);

        if (!result) throw new FileNotFoundException("File was not found");
    }

    @Override
    public void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String mimeType = file.getContentType();

        if (!storageProperties.allowedMimeTypes().contains(mimeType) || mimeType == null) {
            throw new IllegalArgumentException("File type is not valid");
        }
    }

    private String getFileExtension(String originalName) {
        int dotIndex = originalName.lastIndexOf(".");
        return dotIndex == -1 ? "" : originalName.substring(dotIndex + 1);
    }

    private String storageTypeToPath(StorageType storageType) {
        return switch (storageType) {
            case AVATAR -> storageProperties.paths().avatars();
            case BANNER -> storageProperties.paths().banners();
        };
    }
}
