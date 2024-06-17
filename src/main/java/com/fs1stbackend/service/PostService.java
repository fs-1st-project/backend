package com.fs1stbackend.service;

import com.fs1stbackend.dto.PostDTO;
import com.fs1stbackend.dto.PostResponseDTO;
import com.fs1stbackend.model.EntirePost;
import com.fs1stbackend.repository.PostRepository;
import com.fs1stbackend.service.jwt.JwtTokenUtility;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

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

    public List<PostResponseDTO> getAllPost() {
        List<EntirePost> entirePosts = postRepository.getAllPost();

        if (entirePosts.isEmpty()) {
            System.out.println("데이터에 게시물이 없습니다.");
        }

       return entirePosts.stream()
                .map((entirePost) -> new PostResponseDTO(entirePost.getPost(), entirePost.getUserProfile()))
                .collect(Collectors.toList());
    }

}
