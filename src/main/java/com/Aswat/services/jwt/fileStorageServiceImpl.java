package com.Aswat.services.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class fileStorageServiceImpl implements FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir; // Obtenez le chemin de destination à partir des propriétés de l'application

    @Override
    public void init() {

    }

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        // Générer un nom de fichier unique pour éviter les conflits
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        // Définir le chemin de destination complet
        Path targetLocation = Paths.get(uploadDir).resolve(fileName);

        // Copier le fichier vers le chemin de destination
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        // Retourner le chemin relatif du fichier
        return fileName;
    }
}
