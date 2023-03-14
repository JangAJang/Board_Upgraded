package com.board.Board_Upgraded.entity.member;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.entity.base.BaseEntity;
import com.board.Board_Upgraded.entity.base.DueTime;
import com.board.Board_Upgraded.entity.post.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member extends BaseEntity {

    @Column(name = "MEMBER_USERNAME")
    private String username;

    @Column(name = "MEMBER_NICKNAME")
    private String nickname;

    @Column(name = "MEMBER_EMAIL")
    private String email;

    @Column(name = "MEMBER_PASSWORD")
    private String password;

    @Column(name = "MEMBER_ROLE")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();

    public Member(RegisterRequestDto registerRequestDto){
        this.username = registerRequestDto.getUsername();
        this.nickname = registerRequestDto.getNickname();
        this.email = registerRequestDto.getEmail();
        this.role = Role.USER;
        this.password = registerRequestDto.getPassword();
        this.setLastModifiedDate(LocalDateTime.now());
    }

    public void addPost(Post post){
        this.posts.add(post);
    }

    public void changeNickname(String nickname){
        this.nickname = nickname;
    }

    public void changeEmail(String email){
        this.email = email;
    }

    public void changePassword(String password){
        this.password = password;
        setLastModifiedDate(LocalDateTime.now());
    }

    public void deletePost(Post post){
        this.posts.remove(post);
    }

    public boolean isPasswordOutdated(){
        return isLastModifiedDateAfter(DueTime.PASSWORD_CHANGE_DUETIME.getDays());
    }
}
