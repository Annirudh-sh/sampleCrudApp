package com.sample.crud.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "text", nullable = false)
    private String text;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "dateofcomment", nullable = false)
    private LocalDateTime dateOfComment;

    @Column(name = "updated_by")
    private String updatedBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "soft_delete", nullable = false)
    private Boolean softDelete = false;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "additional_column2")
    private String additionalColumn2;

    @Column(name = "additional_column3")
    private String additionalColumn3;

    @Column(name = "additional_column4")
    private String additionalColumn4;

    @Column(name = "additional_column5")
    private String additionalColumn5;

    @Column(name = "additional_column6")
    private String additionalColumn6;

    @Column(name = "additional_column7")
    private String additionalColumn7;

    @Column(name = "additional_column8")
    private String additionalColumn8;

    @Column(name = "additional_column9")
    private String additionalColumn9;

    @Column(name = "additional_column10")
    private String additionalColumn10;

    @PrePersist
    protected void onCreate() {
        this.dateOfComment = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}
