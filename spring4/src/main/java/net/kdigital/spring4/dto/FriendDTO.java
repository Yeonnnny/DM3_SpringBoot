package net.kdigital.spring4.dto;

//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@AllArgsConstructor  // 필요없음
//@NoArgsConstructor   // 기본생성자가 필요한데 생성하지 않으면 JVM이 만들어줌
@Getter
@Setter
@ToString
public class FriendDTO { // 변수명이 보낸 값과 동일해야 함 
	
	private String fname;
	private int age;
	private String phone;
	private LocalDate birthday;
	private boolean active;
	
	
}















