package com.fs1stbackend.web;

import com.fs1stbackend.dto.CommentCreateDTO;
import com.fs1stbackend.dto.PostDTO;
import com.fs1stbackend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/create/{postId}")
    public ResponseEntity<String> createComment(@PathVariable Long postId, @RequestBody CommentCreateDTO commentCreateDTO) {
        if (commentService.createComment(postId, commentCreateDTO).isEmpty()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("해당 게시글 댓글 생성에 실패하였습니다.");
        }
        return ResponseEntity.ok("해당 게시글에 댓글 작성이 성공적으로 완료되었습니다.");
    }

}
