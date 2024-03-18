package net.kdigital.spring5.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;

                             
@Controller
@RequestMapping("/display") // 한 클래스 내에서 여러 요청에 대한 여러 메소드를 정의하기 위해!!
@Slf4j
public class ThymeleafObjectController {
	
	@GetMapping("/object")
	public String object(Model model) {
		
		String korean ="동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라 만세";
		String english = "I have a dream, a song to sing. To help me cope with anything";
				
		
		model.addAttribute("korean", korean);
		model.addAttribute("english", english);
		
		return "thyme_object";
	}
	
}
