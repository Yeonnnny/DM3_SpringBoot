package net.kdigital.spring5.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.spring5.dto.FriendDTO;

                             
@Controller
@RequestMapping("/display") // 한 클래스 내에서 여러 요청에 대한 여러 메소드를 정의하기 위해!!
@Slf4j
public class ThymeleafCollectionController {
	
	@GetMapping("/collection")
	public String collection(Model model) {
		
//		List<String> list = new ArrayList<>();
//		
//		list.add("사과");
//		list.add("바나나");
//		list.add("복숭아");
//		list.add("오렌지");
//		list.add("포도");
//		list.add("귤");
		
		
		List<String> list = Arrays.asList("사과","바나나","복숭아","오렌지","포도","귤");
		
		list.forEach((item)->System.out.println(item)); // 타입 추론: item에 타입을 지정하지 않아도 오류가 안남.. 이 코드는 자바스크립트 코드임
//		for(String s :list) {
//			System.out.println(s);
//		}
		
		String lunch ="순두부찌개,라면,콩국,도시락,3분카레";
		
		
		List<FriendDTO> friendList = Arrays.asList(
				new FriendDTO("손오공", 25, "010-1111-2222", LocalDate.now(), true),
				new FriendDTO("삼장법사", 26, "010-1111-3333", LocalDate.now(), true),
				new FriendDTO("사오정", 27, "010-1111-4444", LocalDate.now(), false)
				);
		
		List<String> numList = new ArrayList<>();
		for(int i =1; i<=30;i++) {
			numList.add("Current Number="+i);
		}
		
		
		Map<String, FriendDTO> map = new HashMap<>();
		map.put("son", new FriendDTO("손오공", 25, "010-1111-2222", LocalDate.now(), true));
		map.put("sam", new FriendDTO("삼장법사", 26, "010-1111-3333", LocalDate.now(), true));
		map.put("sa", new FriendDTO("사오정", 27, "010-1111-4444", LocalDate.now(), false));
		
		
		model.addAttribute("list",list);
		model.addAttribute("map",map);
		model.addAttribute("lunch",lunch);
		model.addAttribute("friendList",friendList);
		model.addAttribute("numList",numList);
		
		return "thyme_collection";
	}
	
}
