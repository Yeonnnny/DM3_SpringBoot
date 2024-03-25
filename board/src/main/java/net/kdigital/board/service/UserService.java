package net.kdigital.board.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.kdigital.board.dto.UserDTO;
import net.kdigital.board.entity.UserEntity;
import net.kdigital.board.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // Bean으로 등록해놨기 때문에 따로 클래스 생성하지 않아도 됨

    /**
     * 회원가입
     * @param userDTO
     */
    public boolean joinProc(UserDTO userDTO) {
        boolean isExistUser = userRepository.existsById(userDTO.getUserId());
        
        if (isExistUser) return false;// 이미 존재하는 아이디이므로 가입 실패
        
        // 비밀번호 암호화
        userDTO.setUserPwd(bCryptPasswordEncoder.encode(userDTO.getUserPwd())); 

        // DTO -> Entity로 변경
        UserEntity entity = UserEntity.toEntity(userDTO);
        
        // DB에 저장
        userRepository.save(entity);

        return true;  // 가입 성공
    }
}
