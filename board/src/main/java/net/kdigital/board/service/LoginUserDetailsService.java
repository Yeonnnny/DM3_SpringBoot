package net.kdigital.board.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.board.dto.LoginUserDetails;
import net.kdigital.board.dto.UserDTO;
import net.kdigital.board.entity.UserEntity;
import net.kdigital.board.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // userId 검증 로직 필요. 테이블에 접근해서 데이터를 가져옴
        // 사용자가 로그인을 하면 SecurityConfig가 username을 여기로 전달함
        log.info("UserId : {}", userId);

        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isPresent()) {
            UserEntity entity = userEntity.get(); 
            UserDTO userDTO = UserDTO.toDTO(entity);
            // 반환을 UserDetails 로 반환해야 하므로 UserDTO를 UserDetails로 바꿔야 함
            return new LoginUserDetails(userDTO);
        }
        // 저장된 유저정보가 없을 때
        return null;
    }
    
}
