package com.example.board_upgrade.Service;

import com.example.board_upgrade.Dto.RegisterDto;
import com.example.board_upgrade.Entity.User;
import com.example.board_upgrade.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User register(RegisterDto registerDto){
        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(registerDto.getPassword()));
        user.setRole("ROLE_USER");
        return userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findUser(int id){
        return userRepository.findById(id).orElseThrow(()-> {
            return new IllegalArgumentException("해당 ID를 찾을 수 없습니다. ");
        });
    }
}
