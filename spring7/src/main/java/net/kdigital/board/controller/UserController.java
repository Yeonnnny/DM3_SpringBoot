package net.kdigital.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.board.dto.UserDTO;
import net.kdigital.board.service.UserService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	/**
	 * 회원가입을 위한 화면 요청
	 * @return
	 */
	@GetMapping("/user/join")
	public String join() {
		return "user/join";
	}
	
	/**
	 * 회원가입 처리
	 * @param userDTO
	 * @return
	 */
	@PostMapping("/user/joinProc")
	public String joinProc(@ModelAttribute UserDTO userDTO) {
		log.info("{}", userDTO.toString());
		
		// 롤, 계정 활성화 여부 세팅
		userDTO.setRoles("ROLE_USER");
		userDTO.setEnabled(true);
		
		userService.joinProc(userDTO);
		return "redirect:/";
	}
	
	// 로그인 화면 요청 --> 주의) 로그인 처리는 Controller에 넣지 않는다.
	@GetMapping("/user/login")
	public String login(
			@RequestParam(value="error", required=false) String error 
			, @RequestParam(value="errMessage", required=false) String errMessage 
			, Model model
			) {
		
		model.addAttribute("error", error);
		model.addAttribute("errMessage", errMessage);
		
		return "user/login";
	}
	
	@PostMapping("/user/confirmId")
	@ResponseBody
	public Boolean confirmId(@RequestParam(name="userId") String userId) {
		
		return true;
	}
}







