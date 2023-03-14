package com.board.Board_Upgraded.domain.member;

import com.board.Board_Upgraded.entity.post.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class MemberPosts {

    @OneToMany
    @JsonIgnore
    private final List<Post> posts = new ArrayList<>();

    public void addPost(Post post){
        posts.add(post);
    }

    public void removePost(Post post){
        posts.remove(post);
    }

    public List<Post> getMyPosts(){
        return posts;
    }
}
