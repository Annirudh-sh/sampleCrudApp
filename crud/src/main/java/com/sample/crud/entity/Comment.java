package com.sample.crud.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "_by", nullable = false)
    private String by;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "dateofcomment", nullable = false)
    private LocalDateTime dateOfComment;
}
