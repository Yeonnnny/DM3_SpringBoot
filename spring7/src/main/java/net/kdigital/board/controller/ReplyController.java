package net.kdigital.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.board.dto.ReplyDTO;
import net.kdigital.board.service.ReplyService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {
	private final ReplyService replyService;
	
	@PostMapping("/replyInsert")
	@ResponseBody
	public ReplyDTO replyInsert(@ModelAttribute ReplyDTO replyDTO) {
		log.info("{}", replyDTO);
		ReplyDTO saveResult = replyService.replyInsert(replyDTO);
		
		return saveResult;
	}
	
	/* 전체 조회 */
	@GetMapping("/replyAll")
	@ResponseBody
	public List<ReplyDTO> replyAll(
			@RequestParam(name="boardNum") Long boardNum
		) {
		log.info("{}", boardNum);
		List<ReplyDTO> replyList = replyService.replyAll(boardNum);
		
		log.info("===> {}", replyList);
		return replyList;
	}
	
	/**
	 * 	리플 삭제 
	 */
	@GetMapping("/replyDelete")
	@ResponseBody
	public  Boolean replyDelete(@RequestParam(name="replyNum") Long replyNum) {
		log.info("delete : {}", replyNum);
		return replyService.replyDelete(replyNum);
	}
}







