package com.board.Board_Upgraded.repository;

import com.board.Board_Upgraded.entity.member.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {


}
