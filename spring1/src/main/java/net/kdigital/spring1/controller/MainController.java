package net.kdigital.spring1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller // Spring Container의 관리 하에 들어감 : 객체를 생성하고, 소멸하는 객체의 라이프사이클
public class MainController {
	
	
	@GetMapping({"","/"}) 
	public String index(){   // 무조건 public String 
		return "index";      // templetes/index.html 을 의미함. 내가 맘대로 바꿀 수 없음
	}
}
