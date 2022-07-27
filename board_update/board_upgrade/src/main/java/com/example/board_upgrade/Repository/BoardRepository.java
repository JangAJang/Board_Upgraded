package com.example.board_upgrade.Repository;

import com.example.board_upgrade.Entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
}
