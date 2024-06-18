package com.fs1stbackend.repository;

import com.fs1stbackend.dto.CommentCreateDTO;
import com.fs1stbackend.model.EntireComment;
import com.fs1stbackend.model.EntirePost;
import com.fs1stbackend.service.exception.UserNotFoundException;
import com.fs1stbackend.service.mapper.EntireCommentRowMapper;
import com.fs1stbackend.service.mapper.EntirePostRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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

    public List<EntireComment> getAllComment(Long postId) {
        String allCommentSql = "SELECT c.id, c.comment_content, c.created_at, c.user_id, f.profile_picture, f.full_name, f.introduction " +
                                "FROM comments c " +
                                "JOIN users u ON c.user_id = u.id " +
                                "JOIN user_profiles f ON f.user_id = u.id " +
                                "WHERE c.post_id = ?";

        return jdbcTemplate.query(allCommentSql, new Object[]{postId}, new EntireCommentRowMapper());
    }

    public String updateComment(Long postId, Long commentId, String content) {
        String sql = "UPDATE comments SET comment_content = ? WHERE post_id = ? AND id = ?";
        int rowsAffected = jdbcTemplate.update(sql, content, postId, commentId);

        return rowsAffected > 0 ? "Comment update successful" : "Comment update failed";
    }


}
