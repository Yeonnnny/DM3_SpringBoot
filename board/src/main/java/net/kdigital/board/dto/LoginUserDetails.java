package net.kdigital.board.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.ToString;

@ToString
public class LoginUserDetails implements UserDetails {
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

        return null;
    }
    
    @Override
    public String getPassword() {
        return this.userPwd;
    }
    
    @Override
    public String getUsername() {  // Security에서 username은 id를 말함
        return this.userId;
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
