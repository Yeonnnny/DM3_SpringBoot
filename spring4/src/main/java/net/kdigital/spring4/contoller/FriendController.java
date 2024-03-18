package net.kdigital.spring4.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;
import net.kdigital.spring4.dto.FriendDTO;

@Controller
@Slf4j
public class FriendController {
	
	@PostMapping("/regist")
	public String regist(
			@ModelAttribute FriendDTO friendDTO,
			Model model
			) {
		
		log.info("{}",friendDTO.toString());
		
		model.addAttribute("friend", friendDTO);
		
		return "registResult"; // forwarding 응답방식 : Model에 데이터 실어나를 수 있음
	}
	
}
