package com.board.Board_Upgraded.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @CreatedDate
    @Column(name = "CREATED_TIME", nullable = false, updatable = false)
    private LocalDateTime createdTime;


    @Column(name = "LAST_MODIFIED_TIME", nullable = false)
    private LocalDateTime lastModifiedTime;
}
