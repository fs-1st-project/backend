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
        System.out.println("클라이언트 게시물 작성 컨트롤러에 닿았습니다");
        if (postService.createPost(token,postDTO).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 유저로는 게시글을 작성할 수 없습니다.");
        }
        return ResponseEntity.ok("게시글 작성이 성공적으로 완료되었습니다.");
    }

    @GetMapping("/read")
    public ResponseEntity<List<PostResponseDTO>> getAllPost() {
        return ResponseEntity.ok(postService.getAllPost());
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable Long postId, @RequestBody PostContentUpdateDTO postContentUpdateDTO) {
        String isPostUpdateSuccess = postService.updatePost(postId, postContentUpdateDTO);

        if (isPostUpdateSuccess.equals("Post update failed")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(isPostUpdateSuccess);
        }
        return ResponseEntity.ok(isPostUpdateSuccess);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        String isDeleteSuccess = postService.deletePost(postId);

        if (isDeleteSuccess.equals("Post delete failed")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(isDeleteSuccess);
        }
        return ResponseEntity.ok(isDeleteSuccess);
    }
}
