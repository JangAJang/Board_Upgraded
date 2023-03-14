package com.board.Board_Upgraded.entity.member;

import com.board.Board_Upgraded.domain.member.MemberInfo;
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
    @Embedded private MemberInfo memberInfo;
    @Column(name = "MEMBER_ROLE")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public Member(RegisterRequestDto registerRequestDto){
        username = new Username(registerRequestDto.getUsername());
        memberInfo = new MemberInfo(registerRequestDto.getNickname(), registerRequestDto.getEmail());
        role = Role.USER;
        password = new Password(registerRequestDto.getPassword());
        this.setLastModifiedDate(LocalDateTime.now());
    }

    public String getUsername(){
        return username.getUsername();
    }

    public boolean isPasswordSameWithBefore(String password, PasswordEncoder passwordEncoder){
        return this.password.isRightPassword(passwordEncoder, password);
    }

    public void changeMemberInfo(MemberInfo memberInfo){
        this.memberInfo = memberInfo;
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

    public String getNickname(){
        return memberInfo.getNickname();
    }

    public String getEmail(){
        return memberInfo.getEmail();
    }

    public String getPassword(){
        return password.getPassword();
    }
}
