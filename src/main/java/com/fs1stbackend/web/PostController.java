package com.fs1stbackend.web;

import com.fs1stbackend.dto.PostContentUpdateDTO;
import com.fs1stbackend.dto.PostDTO;
import com.fs1stbackend.dto.PostResponseDTO;
import com.fs1stbackend.service.PostService;
import com.fs1stbackend.service.jwt.JwtTokenUtility;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> createPost(@RequestHeader("Authorization") String token, @RequestBody PostDTO postDTO) {
        if (postService.createPost(token,postDTO).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 유저로는 게시글을 작성할 수 없습니다.");
        }
        return ResponseEntity.ok("게시글 작성이 성공적으로 완료되었습니다.");
    }

    @GetMapping("/read")
    public ResponseEntity<List<PostResponseDTO>> getAllPost() {
        return ResponseEntity.ok(postService.getAllPost());
    }

    @PutMapping("/update/{contentId}")
    public ResponseEntity<?> updatePost(@PathVariable Long contentId, @RequestBody PostContentUpdateDTO postContentUpdateDTO) {
        String isUpdateSuccess = postService.updatePost(contentId, postContentUpdateDTO);

        if (isUpdateSuccess.equals("Update failed")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(isUpdateSuccess);
        }
        return ResponseEntity.ok(isUpdateSuccess);
    }
}
