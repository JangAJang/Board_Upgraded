package com.example.board_upgrade.Repository;

import com.example.board_upgrade.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByName(String name);
}
