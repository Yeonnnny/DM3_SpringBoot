package net.kdigital.board.dto;

import java.util.Collection;
import java.util.ArrayList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.ToString;

@ToString
public class LoginUserDetails implements UserDetails { 
    private static final long serialVersionUID = 1L; // 외부의 역직렬화한 데이터와 같은지 비교해야 하는데 그때 시리얼 번호가 있어야 함
    private String userId;
    private String userName;
    private String userPwd;
    private String email;
    private String roles;
    private Boolean enabled;

    // 생성자
    public LoginUserDetails(UserDTO userDTO) {
        super();
        this.userId = userDTO.getUserId();
        this.userName = userDTO.getUserName();
        this.userPwd = userDTO.getUserPwd();
        this.email = userDTO.getEmail();
        this.roles = userDTO.getRoles();
        this.enabled = userDTO.getEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 굳이 list로 하는 이유 : role이 여러 개인 경우 테이블(roles)을 따로 만들어서 사용하는데, 이를 관리하기 위한 목적임 
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
    
    // ID 반환 (Override) - Security가 설정한 값이므로 PK를 써야함. 변경하면 안됨
    @Override
    public String getUsername() {  // Security에서 username은 id를 말함
        return this.userId;
    }
    
    /**
     * 진짜 회원 이름 반환하는 함수 (사용자 정의 함수)
     * @return
     */
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
