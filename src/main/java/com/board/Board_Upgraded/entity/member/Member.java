package com.board.Board_Upgraded.entity.member;

import com.board.Board_Upgraded.domain.member.MemberPosts;
import com.board.Board_Upgraded.domain.member.Password;
import com.board.Board_Upgraded.domain.member.Username;
import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.entity.base.BaseEntity;
import com.board.Board_Upgraded.entity.base.DueTime;
import com.board.Board_Upgraded.entity.post.Post;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member extends BaseEntity {
    @Embedded private Username username;
    @Embedded private Password password;
    @Embedded private MemberPosts memberPosts;
    @Column(name = "MEMBER_NICKNAME")
    private String nickname;
    @Column(name = "MEMBER_EMAIL")
    private String email;
    @Column(name = "MEMBER_ROLE")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public Member(RegisterRequestDto registerRequestDto){
        this.username = new Username(registerRequestDto.getUsername());
        this.nickname = registerRequestDto.getNickname();
        this.email = registerRequestDto.getEmail();
        this.role = Role.USER;
        this.password = new Password(registerRequestDto.getPassword());
        this.setLastModifiedDate(LocalDateTime.now());
    }

    public String getUsername(){
        return username.getUsername();
    }

    public boolean isPasswordSameWithBefore(String password, PasswordEncoder passwordEncoder){
        return this.password.isRightPassword(passwordEncoder, password);
    }

    public void changeNickname(String nickname){
        this.nickname = nickname;
    }

    public void changeEmail(String email){
        this.email = email;
    }

    public void changePassword(String password){
        this.password = new Password(password);
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
