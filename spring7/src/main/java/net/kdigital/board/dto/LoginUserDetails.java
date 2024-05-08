package net.kdigital.board.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ToString
@Slf4j
public class LoginUserDetails implements UserDetails {
	private static final long serialVersionUID = 1L;
	private String userId;
	private String userName;
	private String userPwd;
	private String email;
	private String roles;
	private Boolean enabled;
	
	// 생성자
	public LoginUserDetails(UserDTO userDTO) {
		this.userId   = userDTO.getUserId();
		this.userName = userDTO.getUserName();
		this.userPwd  = userDTO.getUserPwd();
		this.email    = userDTO.getEmail();
		this.roles    = userDTO.getRoles();
		this.enabled  = userDTO.getEnabled();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();
		collection.add(new GrantedAuthority() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getAuthority() {
				
				return roles;
			}
		});
		return collection;
	}

	@Override
	public String getPassword() {
		return this.userPwd;
	}

	// ID 반환 (Override) - Security가 설정한 값이므로 변경하지 마시오
	@Override
	public String getUsername() {
		return this.userId;
	}

	// 이름 반환 - 사용자 정의 메소드
	public String getUserName() {
		return this.userName;
	}

	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}





}
