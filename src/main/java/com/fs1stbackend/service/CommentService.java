package com.fs1stbackend.service;

import com.fs1stbackend.dto.CommentCreateDTO;
import com.fs1stbackend.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public String createComment(@PathVariable Long postId, @RequestBody CommentCreateDTO commentCreateDTO){
        String createCommentResponse = "";

        try {
            createCommentResponse = commentRepository.createComment(postId, commentCreateDTO);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("댓글 생성 서비스 로직 중 예외 발생");
        }
        return createCommentResponse;
    }
}
