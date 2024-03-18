package net.kdigital.test2.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {   // Bean : Spring Container가 관리하는 객체
	
	@GetMapping({"/",""})
	public String index(Model model) {
		model.addAttribute("name","홍길동");
		model.addAttribute("age",25);
		model.addAttribute("join_date",LocalDate.now());		
		return "index"; //forwarding으로 응답. 메시지도 실어서 보낼 수 있음!(Model)
	}
	
	@GetMapping("/input")
	public String input(
			@RequestParam(name = "name", defaultValue = "아무개씨") String name,
			@RequestParam(name = "age", defaultValue = "999") int age,
			Model model
			) {
		
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		model.addAttribute("join_date", LocalDate.now());
		
		return "index";
	}
	
}

/*
 *  localhost/ (엔터) or localhoat
 *  
 *  
 */