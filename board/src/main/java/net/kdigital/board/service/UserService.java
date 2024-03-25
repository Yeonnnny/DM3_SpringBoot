package net.kdigital.board.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.kdigital.board.dto.UserDTO;
import net.kdigital.board.entity.UserEntity;
import net.kdigital.board.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void insertUser(UserDTO userDTO) {
        UserEntity entity = UserEntity.toEntity(userDTO);
        userRepository.save(entity);
    }
}
