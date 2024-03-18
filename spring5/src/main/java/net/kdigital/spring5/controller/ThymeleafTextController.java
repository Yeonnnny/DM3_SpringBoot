package net.kdigital.spring5.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import net.kdigital.spring5.dto.FriendDTO;

@Controller
public class ThymeleafTextController {
	
	@GetMapping("/display/text")
	public String text(Model model) {
		// 기본자료형 출력을 위한 데이터 
		String korean = "대한민국~☆★";
		String english = "Hi, Everyone~";
		int age = 25;
		double pi = 3.141592;
		String tag = "<marquee>돌이 굴러가유~</marquee>";
		String url = "http://naver.com";

		// 객체 자료형 출력을 위한 데이터 
		// Friend를 가져와서 할 예정
		FriendDTO f1 = new FriendDTO();
		FriendDTO f2 = new FriendDTO("삼장법사", 45, "010-2222-3333", LocalDate.of(1980, 12, 10), true);
		FriendDTO f3 = new FriendDTO("사오정", 24, "010-1111-2222", LocalDate.of(2001, 12, 10), true);
		
		
		// 숫자 데이터 
		int n1 = 1_234_000;
		double n2 = 5_123_456.3456789;
		double n3 = 56.3456789;
		double n4 = 0.05;
		
		
		// 날짜 관련 데이터
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		
		LocalDate localDate = LocalDate.now();
		LocalDateTime today = LocalDateTime.now();
		
		
		
		model.addAttribute("korean", korean);
		model.addAttribute("english", english);
		model.addAttribute("age", age);
		model.addAttribute("pi", pi);
		model.addAttribute("tag", tag);
		model.addAttribute("url", url);
		
		model.addAttribute("f1",f1);
		model.addAttribute("f2",f2);
		model.addAttribute("f3",f3);
		
		model.addAttribute("n1",n1);
		model.addAttribute("n2",n2);
		model.addAttribute("n3",n3);
		model.addAttribute("n4",n4);
		
		model.addAttribute("date",date);
		model.addAttribute("calendar",calendar);
		model.addAttribute("localDate",localDate);
		model.addAttribute("today",today);
		
		return "thyme_text";
	}
	

	
	
}
