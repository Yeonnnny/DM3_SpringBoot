package net.kdigital.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.kdigital.board.entity.UserEntity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class UserDTO {
	private String userId;
	private String userName;
	private String userPwd;
	private String email;
	private String roles;
	private Boolean enabled;
	
	
	// Entity --> DTO
	public static UserDTO toDTO(UserEntity userEntity) {
		return UserDTO.builder()
				.userId(userEntity.getUserId())
				.userName(userEntity.getUserName())
				.userPwd(userEntity.getUserPwd())
				.email(userEntity.getEmail())
				.roles(userEntity.getRoles())
				.enabled(userEntity.getEnabled())
				.build();
	}
}






