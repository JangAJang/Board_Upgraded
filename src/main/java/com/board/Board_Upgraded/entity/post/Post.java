package com.board.Board_Upgraded.entity.post;

import com.board.Board_Upgraded.domain.post.Content;
import com.board.Board_Upgraded.domain.post.Title;
import com.board.Board_Upgraded.entity.base.BaseEntity;
import com.board.Board_Upgraded.entity.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Builder
    public Post(Title title, Content content, Member member){
        this.title = title;
        this.content = content;
        this.member = member;
        member.addPost(this);
        setLastModifiedDate(LocalDateTime.now());
    }

    public String getTitle(){
        return title.getTitle();
    }

    public String getContent(){
        return content.getContent();
    }

    public void editTitle(Title title){
        this.title = title;
        setLastModifiedDate(LocalDateTime.now());
    }

    public void editContent(Content content){
        this.content = content;
        setLastModifiedDate(LocalDateTime.now());
    }

    public boolean isWriter(Member member){
        return this.member.equals(member);
    }

    public String getWritersName(){
        return member.getNickname();
    }
}
