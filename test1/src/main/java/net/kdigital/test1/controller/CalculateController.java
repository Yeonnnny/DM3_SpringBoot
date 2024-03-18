package net.kdigital.test1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CalculateController {
	
	@GetMapping("/calculate")
	public String param(
			@RequestParam(name = "x", defaultValue = "0") int x,
			@RequestParam(name = "y", defaultValue = "0") int y,
			Model model
			) {
		
		log.info("{} + {} = {}",x,y,x+y);
		
		model.addAttribute("x", x);
		model.addAttribute("y", y);
		model.addAttribute("result", x+y);
		
		return "result";
	}
}
