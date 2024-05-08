package com.Aswat.services.jwt;

import com.Aswat.Dtos.PostDTO;
import com.Aswat.entity.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public interface PostService {


  PostDTO createPost(PostDTO postDTO, MultipartFile file) throws IOException;

}
