package net.kdigital.spring3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ParamController {
	
	@GetMapping({"/param/send","/param/sendForm"})
	public String param(
			@RequestParam(name="name", 	defaultValue = "홍길동") String name,
			@RequestParam(name="age", 	defaultValue = "30") int age, 
			Model model
			) {
		log.info("전송받은 데이터 : 이름-{}, 나이-{}",name,age);
		
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		
		return "result"; 	// result.html // forwarding 방식으로 응답 (추가 : redirect 방식도 있음)
	}
	
	
	
 

}
