package net.kdigital.spring6.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder	// @AllArgsConstructor가 있어야 함
public class Sample {
	private Long seq;
	private String userid;
	private String pwd;
	private String content;
	
	@Builder
	public Sample(String userid, String content) {
		this.userid = userid;
		this.content = content;
	}
	
}




