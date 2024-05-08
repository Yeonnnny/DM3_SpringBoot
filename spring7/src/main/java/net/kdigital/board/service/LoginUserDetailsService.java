package net.kdigital.board.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.board.dto.LoginUserDetails;
import net.kdigital.board.dto.UserDTO;
import net.kdigital.board.entity.UserEntity;
import net.kdigital.board.handler.CustomLoginSuccessHandler;
import net.kdigital.board.repository.UserRepository;

@Configuration
@Service
@Slf4j
@RequiredArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;
	
	private Map<String, User> roles = new HashMap<>();

    @PostConstruct
    public void init() {
        roles.put("admin", new User("admin", "{noop}admin", getAuthority("ROLE_ADMIN")));
        roles.put("user", new User("user", "{noop}user", getAuthority("ROLE_USER")));
    }

    private List<GrantedAuthority> getAuthority(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
    
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		// userId 검증 로직이 필요. 테이블에 접근해서 데이터를 가져옴
		// 사용자가 로그인을 하면 SecurityConfig가 username을 여기로 전달함
		UserEntity userEntity =  userRepository.findById(userId)
				.orElseThrow(() -> {
					throw new UsernameNotFoundException("error 발생");
				});
		
		UserDTO userDTO= UserDTO.toDTO(userEntity);
		
		// 반환을 UserDetails로 반환해야 하므로 UserDTO를 UserDetails로 바꿔야함!
//		
		log.info("loadUserByUsername()==========={}", roles.get(userId));
		return new LoginUserDetails(userDTO);
	
	}
	
	@Bean
	public AuthenticationSuccessHandler customSuccessHandler(){
	    return new CustomLoginSuccessHandler();
	}

}
