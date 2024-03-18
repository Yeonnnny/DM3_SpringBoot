package net.kdigital.spring2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {
	// 하나의 메소드를 구동시키는 요청이 여러 개면 중괄호 안에 넣음, 순서는 상관없음 {"/", ""}
	@GetMapping({"/",""})
	public String index() {
		log.info("도착");
		return "index";
	}

}
