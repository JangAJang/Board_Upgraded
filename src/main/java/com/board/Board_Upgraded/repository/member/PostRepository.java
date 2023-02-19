package com.board.Board_Upgraded.repository.member;

import com.board.Board_Upgraded.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
