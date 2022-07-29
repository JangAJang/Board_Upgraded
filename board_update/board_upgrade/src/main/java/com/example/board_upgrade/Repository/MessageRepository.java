package com.example.board_upgrade.Repository;

import com.example.board_upgrade.Entity.Message;
import com.example.board_upgrade.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findAllByReceiver(User user);
    List<Message> findAllBySender(User user);
}
