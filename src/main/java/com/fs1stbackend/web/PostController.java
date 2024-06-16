package com.fs1stbackend.web;

import com.fs1stbackend.dto.PostDTO;
import com.fs1stbackend.service.PostService;
import com.fs1stbackend.service.jwt.JwtTokenUtility;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<PostDTO> createPost(@RequestHeader("Authorization") String token, @RequestBody PostDTO postDTO) {
        Claims claims = JwtTokenUtility.extractClaims(token.substring(7));
        String email = claims.getSubject();
        if (postDTO.getCreated_at() == null){
            postDTO.setCreated_at(LocalDateTime.now());
        }
        if (postDTO.getImage().isEmpty()){
            postDTO.setImage("");
        }
        return ResponseEntity.ok(postService.createPost(postDTO));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<PostDTO> updatePost(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody PostDTO postDTO) {
        Claims claims = JwtTokenUtility.extractClaims(token.substring(7));
        String email = claims.getSubject();
        // Assuming user validation logic here
        return ResponseEntity.ok(postService.updatePost(id, postDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePost(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Claims claims = JwtTokenUtility.extractClaims(token.substring(7));
        String email = claims.getSubject();
        // Assuming user validation logic here
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

}
