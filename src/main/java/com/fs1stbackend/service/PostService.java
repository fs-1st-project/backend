package com.fs1stbackend.service;

import com.fs1stbackend.dto.PostDTO;
import com.fs1stbackend.model.Post;
import com.fs1stbackend.repository.PostRepository;
import com.fs1stbackend.service.jwt.JwtTokenUtility;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Transactional
    public String createPost(@RequestHeader("Authorization") String token, @RequestBody PostDTO postDTO) {
        String saveResponse="";
        Claims claims = JwtTokenUtility.extractClaims(token.substring(7));
        String email = claims.getSubject();
        try {
            saveResponse = postRepository.save(email, postDTO);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("서비스 로직 중 예외 발생");
        }
        return saveResponse;
    }

//    @Transactional
//    public PostDTO updatePost(Long id, PostDTO postDTO) {
//        Post post = postRepository.findById(id);
//        if (post != null) {
//            post.setContent(postDTO.getContent());
//            post.setImage(Base64.getDecoder().decode(postDTO.getImage()));
//            Post updatedPost = postRepository.update(post);
//            return postDTO;
//        }
//        return null;
//    }
//
//    @Transactional
//    public void deletePost(Long id) {
//        postRepository.deleteById(id);
//    }
//
//    public PostDTO getPost(Long id) {
//        Post post = postRepository.findById(id);
//        if (post != null) {
//            PostDTO postDTO = new PostDTO();
//            postDTO.setId(post.getId());
//            postDTO.setContent(post.getContent());
//            postDTO.setImage(Base64.getEncoder().encodeToString(post.getImage()));
//            return postDTO;
//        }
//        return null;
//    }
//
//    public List<PostDTO> getAllPosts() {
//        List<Post> posts = postRepository.findAll();
//        return posts.stream().map(post -> {
//            PostDTO postDTO = new PostDTO();
//            postDTO.setId(post.getId());
//            postDTO.setContent(post.getContent());
//            postDTO.setImage(Base64.getEncoder().encodeToString(post.getImage()));
//            return postDTO;
//        }).collect(Collectors.toList());
//    }

}
