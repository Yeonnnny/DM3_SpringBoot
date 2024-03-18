package net.kdigital.spring6.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.spring6.dto.FriendDTO;
import net.kdigital.spring6.service.FriendService;

@Controller
@Slf4j
@RequiredArgsConstructor // final이 붙어있는 멤버를 생성 및 포기화할 때 사용하는 Annotation
public class FriendController {

	private final FriendService service; // FriendService 사용을 위한 선언

	// Annotation이나 생성자 주입방식 둘 중에 하나 골라서 사용하면 됨!
	// 생성자 주입방식 (예전에는 @Autowired라는 애노테이션 사용)
	// 원래는 service = new FriendService(); 로 생성함
	// 그런데, FriendService에 @Service를 걸어 주었기 때문에 Spring Container가
	// 관리함(FriendService클래스를 빈으로 서블릿 컨테이너에 등록해줌)
	// public FriendController(FriendService service) { // Spring Container가 매개 변수를
	// 던져줌 : DI(Dependency Injection)
	// this.service = service;
	// }

	@GetMapping("/insert")
	public String insert(Model model) { // 아래 BindingResult를 담는 것과 형식을 맞춰줘야 하기 때문에
		model.addAttribute("friendDTO", new FriendDTO()); // 빈 생성자 보냄 (에러를 담는 객체를 보내는 것과 동일하게 작용하도록하기 위해 껍데기만 있는 객체를
															// 보냄?)
		return "insertForm"; // forwarding 방식
	}

	@PostMapping("/insert")
	public String insert(
			@Valid @ModelAttribute FriendDTO friendDTO, BindingResult bindingResult) { // BindingResult : 오류 담는 객체

		log.info("{}", friendDTO.toString());
		log.info("bindingResult : {}", bindingResult); // 오류 확인

		if (bindingResult.hasErrors()) {
			log.info("친구 정보 등록 실패 (오류 포함)");
			return "insertForm"; // forwarding 방식임에도 불구하고 model에 담지 않아도 에러 포함된 상태로 화면 이동함 (model에 넣은 것처럼 행동)
		}
		service.insertFriend(friendDTO); // service 클래스의 메소드 요청

		return "redirect:/"; // redirect 방식 : 클라이언트(브라우저)로 하여금 / 요청(Getmapping)을 다시 하라는 의미
	}

	@GetMapping("/list")
	public String list(Model model) {

		List<FriendDTO> friendList = service.selectAll();

		model.addAttribute("list", friendList);

		return "list";
	}

	@GetMapping("/deleteOne")
	public String deleteOne(@RequestParam(name = "friendSeq", defaultValue = "0") Long friendSeq) {

		log.info("삭제할 seq : {}", friendSeq);
		service.deleteOne(friendSeq);

		return "redirect:/list";
	}

	@GetMapping("/updateOne")
	public String updateOne(@RequestParam(name = "friendSeq", defaultValue = "0") Long friendSeq, Model model) {
 
		log.info("수정할 seq : {}", friendSeq);
		FriendDTO friendDTO = service.selectOne(friendSeq);
		log.info("수정할 데이터 : {}", friendDTO.toString());
		model.addAttribute("friendDTO", friendDTO);

		return "updateForm";
	}

	@PostMapping("/updateProc")
	public String updateProc(
			@Valid @ModelAttribute FriendDTO friendDTO, BindingResult bindingResult) { // 객체를 Model 컨테이너로 받음

		log.info("{}", friendDTO.toString());
		log.info("bindingResult : {}", bindingResult);

		if (bindingResult.hasErrors()) {
			log.info("친구 정보 수정 실패 (오류가 포함됨)");
			return "updateForm"; // 에러 포함된 상태로 수정 폼으로 감
		}

		service.updateProc(friendDTO); // service 클래스의 메소드 요청

		return "redirect:/list";
	}
}
