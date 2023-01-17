package com.board.Board_Upgraded.entity.base;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public class BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @CreatedDate
    @Column(name = "CREATED_TIME", nullable = false, updatable = false)
    private LocalDateTime createdDate;


    @Column(name = "LAST_MODIFIED_TIME", nullable = false)
    private LocalDateTime lastModifiedDate;

    public boolean isLastModifiedDateAfter(Long days){
        return LocalDateTime.now().minusDays(days).isAfter(this.lastModifiedDate);
    }
}
