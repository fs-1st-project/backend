package com.fs1stbackend.service;

import com.fs1stbackend.dto.CommentAllResponseDTO;
import com.fs1stbackend.dto.CommentContentUpdateDTO;
import com.fs1stbackend.dto.CommentCreateDTO;
import com.fs1stbackend.dto.PostResponseDTO;
import com.fs1stbackend.model.EntireComment;
import com.fs1stbackend.model.EntirePost;
import com.fs1stbackend.repository.CommentRepository;
import com.fs1stbackend.service.exception.NoContentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<CommentAllResponseDTO> getAllComment(@PathVariable Long postId) {
        List<EntireComment> entireComments = commentRepository.getAllComment(postId);

        if (entireComments.isEmpty()) {
            System.out.println("데이터에 해당 게시물의 댓글이 없습니다.");
        }

        return entireComments.stream()
                .map((entireComment) -> new CommentAllResponseDTO(entireComment.getComment(), entireComment.getUserProfile()))
                .collect(Collectors.toList());
    }

    public String updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentContentUpdateDTO commentContentUpdateDTO){
        String content = commentContentUpdateDTO.getCommentContent();
        String isCommentUpdateSuccess = "";

        try {
            if (content != null && !content.isEmpty()) {
                isCommentUpdateSuccess = commentRepository.updateComment(postId, commentId, content);
            } else {
                throw new NoContentException("수정 할 댓글의 내용이 없습니다");
            }
        } catch (Exception e) {
           e.printStackTrace();
            System.out.println("댓글 업데이트 서비스 로직 중 예외 발생");
        }
        return isCommentUpdateSuccess;
    }
}
