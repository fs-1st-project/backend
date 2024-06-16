package com.fs1stbackend.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@Entity
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Lob
    private byte[] image;

    @Column(name = "created_at")
    private Timestamp created_at;

    public Post() {
    }

    public Post(Long id, String content, byte[] image, Timestamp created_at) {
        this.id = id;
        this.content = content;
        this.image = image;
        this.created_at = created_at;
    }
}
