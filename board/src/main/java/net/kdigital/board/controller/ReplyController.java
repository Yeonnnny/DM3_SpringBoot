package net.kdigital.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @ResponseBody // JQuery와 연결 - Http 요청의 본문 Body부분이 자바 객체로 전달됨
    public ReplyDTO replyInsert(@ModelAttribute ReplyDTO replyDTO) {
        
        ReplyDTO saveResult = replyService.replyInsert(replyDTO);
        
        return saveResult; // null이 아니면 저장 성공
    }
    
}
