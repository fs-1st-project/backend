package com.fs1stbackend.repository;

import com.fs1stbackend.dto.CommentCreateDTO;
import com.fs1stbackend.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public String createComment(Long postId, CommentCreateDTO commentCreateDTO){
        String createCommentSql = "INSERT INTO comments (comment_content, created_at, user_id, post_id) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(createCommentSql, commentCreateDTO.getCommentContent(), commentCreateDTO.getCreatedAt(), commentCreateDTO.getUserId(), postId);
            return "해당 게시글에 댓글 생성이 성공적으로 완료되었습니다";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "해당 게시글에 댓글 생성을 실패하였습니다";
    }


}
