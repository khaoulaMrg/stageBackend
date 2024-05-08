package com.Aswat.services.jwt;

import com.Aswat.Dtos.PostDTO;
import com.Aswat.entity.Post;
import com.Aswat.reposistories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final FileStorageService fileStorageService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, FileStorageService fileStorageService) {
        this.postRepository = postRepository;
        this.fileStorageService = fileStorageService;
    }



    @Override
    public PostDTO createPost(PostDTO postDTO, MultipartFile file) throws IOException {
        String picPath = fileStorageService.storeFile(file); // Enregistrer le fichier et récupérer le chemin
        Post post = new Post();
        // Set other attributes of post
        post.setPicPath(picPath); // Stocker le chemin de l'image
        postRepository.save(post);
        // Map post to DTO and return
        return postDTO;
    }
}