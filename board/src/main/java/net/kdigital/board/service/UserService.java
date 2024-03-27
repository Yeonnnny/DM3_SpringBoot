package net.kdigital.board.service;

import java.util.Optional;

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

    /**
     * 전달받은 아이디가 DB에 존재하는지 확인하고 존재하면 true, 존재하지 않으면 false반환하는 함수
     * @param userId
     * @return
     */ 
    public UserDTO confirmId(String userId) {
        Optional<UserEntity> entity = userRepository.findById(userId);

        if(entity.isPresent()){
            UserEntity userEntity = entity.get();
            return UserDTO.toDTO(userEntity);
        }

        return null;
    }
}
