package com.Aswat.services.jwt;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {

    void init();

    public String storeFile(MultipartFile file) throws IOException;

    // Autres méthodes de votre interface...
}
