package net.kdigital.spring5.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor 
@Getter
@Setter
@ToString
public class FriendDTO { 
	
	// 변수명이 보낸 값과 동일해야 함 
	private String fname;
	private int age;
	private String phone;
	private LocalDate birthday;
	private boolean active;
	
}















