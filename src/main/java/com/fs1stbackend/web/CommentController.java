package com.fs1stbackend.web;

import com.fs1stbackend.dto.*;
import com.fs1stbackend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/read/{postId}")
    public ResponseEntity<List<CommentAllResponseDTO>> getAllComment(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getAllComment(postId));
    }

    @PutMapping("/update/{postId}/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentContentUpdateDTO commentContentUpdateDTO) {

        String isCommentUpdateSuccess = commentService.updateComment(postId, commentId, commentContentUpdateDTO);
        if(isCommentUpdateSuccess.equals("Comment update failed")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(isCommentUpdateSuccess);
        }
        return ResponseEntity.ok(isCommentUpdateSuccess);
    }

    @DeleteMapping("/delete/{postId}/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId){
        String isDeleteSuccess = commentService.deleteComment(postId, commentId);

        if (isDeleteSuccess.equals("Comment delete failed")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(isDeleteSuccess);
        }
        return ResponseEntity.ok(isDeleteSuccess);
    }

}
