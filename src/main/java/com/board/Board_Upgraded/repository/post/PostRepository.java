package com.board.Board_Upgraded.repository.post;

import com.board.Board_Upgraded.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    Optional<Post> findByTitle(String title);
}
