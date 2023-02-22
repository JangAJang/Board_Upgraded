package com.board.Board_Upgraded.entity.post;

import com.board.Board_Upgraded.entity.base.BaseEntity;
import com.board.Board_Upgraded.entity.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public Post(String title, String content, Member member){
        this.title = title;
        this.content = content;
        this.member = member;
        setLastModifiedDate(LocalDateTime.now());
    }

    public void editTitle(String title){
        this.title = title;
    }

    public void editContent(String content){
        this.content = content;
    }
}
