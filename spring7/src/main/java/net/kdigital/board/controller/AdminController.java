package net.kdigital.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@GetMapping("/adminPage")
	public String adminPage() {
		log.info("관리자 페이지 요청");
		return "admin/adminMain";
	}
}
