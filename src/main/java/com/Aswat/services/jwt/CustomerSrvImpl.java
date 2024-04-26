package com.Aswat.services.jwt;

import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class CustomerSrvImpl implements CustomerService {

    private final NewsRepo newsRepo;
    private final String storageDirectory;

    @Autowired
    public CustomerSrvImpl(NewsRepo newsRepo, @Value("${storage}") String storageDirectory) {
        this.newsRepo = newsRepo;
        this.storageDirectory = storageDirectory;
    }

    public void saveCategory(String name, String description, MultipartFile file) {
        try {
            // Vérifier si le fichier est vide
            if (file.isEmpty()) {
                throw new IllegalArgumentException("Le fichier ne peut pas être vide");
            }

            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isEmpty()) {
                throw new IllegalArgumentException("Le nom de fichier d'origine ne peut pas être null ou vide");
            }

            // Générer un nom de fichier unique pour éviter les collisions
            String fileName = StringUtils.cleanPath(originalFilename);
            String filePath = Paths.get(storageDirectory, fileName).toString();

            // Copier le fichier vers le répertoire de stockage
            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

            // Créer une nouvelle instance de Category avec les données fournies
           Category category = new Category();
            category.setName(name);
            category.setDescription(description);
            category.setFileUrl(fileName); // Affectez le nom du fichier à imagePath

            // Enregistrer la catégorie dans la base de données
            categoryRepo.save(category);
        } catch (IOException ex) {
            // Gérer les exceptions lors du stockage du fichier
            throw new FileStorageException("Could not store file " + file.getOriginalFilename() + ". Please try again!", ex);
        }
    }

    @Override
    public void postCategory(CategoryDto categoryDto) throws IOException {
        // Vous pouvez implémenter le traitement nécessaire ici en utilisant les données de CategoryDto

    }
}