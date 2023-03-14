package com.board.Board_Upgraded.entity.member;

import com.board.Board_Upgraded.domain.member.MemberPosts;
import com.board.Board_Upgraded.domain.member.Username;
import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.entity.base.BaseEntity;
import com.board.Board_Upgraded.entity.base.DueTime;
import com.board.Board_Upgraded.entity.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member extends BaseEntity {
    @Embedded
    private Username username;
//    private MemberInfo memberInfo;
//    private Password password;

    @Embedded
    private MemberPosts memberPosts;

    @Column(name = "MEMBER_NICKNAME")
    private String nickname;

    @Column(name = "MEMBER_EMAIL")
    private String email;

    @Column(name = "MEMBER_PASSWORD")
    private String password;

    @Column(name = "MEMBER_ROLE")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public Member(RegisterRequestDto registerRequestDto){
        this.username = new Username(registerRequestDto.getUsername());
        this.nickname = registerRequestDto.getNickname();
        this.email = registerRequestDto.getEmail();
        this.role = Role.USER;
        this.password = registerRequestDto.getPassword();
        this.setLastModifiedDate(LocalDateTime.now());
    }

    public String getUsername(){
        return username.getUsername();
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

    public void addPost(Post post){
        memberPosts.addPost(post);
    }

    public void deletePost(Post post){
        memberPosts.removePost(post);
    }

    public List<Post> getMembersPost(){
        return memberPosts.getMyPosts();
    }

    public boolean isPasswordOutdated(){
        return isLastModifiedDateAfter(DueTime.PASSWORD_CHANGE_DUETIME.getDays());
    }
}
