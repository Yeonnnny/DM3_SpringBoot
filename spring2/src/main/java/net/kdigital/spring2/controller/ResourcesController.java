package net.kdigital.spring2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResourcesController {
	
	@GetMapping("/image")
	public String image() {
		return "image";
	}
	
	@GetMapping("/css")
	public String css() {
		return "css";
	}
	
	@GetMapping("/javascript")
	public String javascript() {
		return "javascript";  
	}
	
}
