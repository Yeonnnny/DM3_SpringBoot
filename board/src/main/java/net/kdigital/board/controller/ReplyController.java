package net.kdigital.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.board.dto.ReplyDTO;
import net.kdigital.board.service.ReplyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



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

    @GetMapping("/replyAll")
    @ResponseBody
    public List<ReplyDTO> replyAll(@RequestParam(name = "boardNum")Long boardNum) {
        List<ReplyDTO> replyList = replyService.replyAll(boardNum);
        log.info("===> {}", replyList);
        return replyList;
    }

    @GetMapping("/replyDelete") 
    @ResponseBody
    public boolean replyDelete(@RequestParam(name = "replyNum")Long replyNum){
        return replyService.delete(replyNum);
    }


    /**
     * 전달받은 repluNum에 해당하는 ReplyDTO 반환하는 함수 
     * @param replyNum
     * @return
     */
    @GetMapping("/replySelect")
    @ResponseBody
    public ReplyDTO replySelect(@RequestParam(name = "replyNum")Long replyNum){
        return replyService.selectOne(replyNum);
    }


    /**
     * 
     * @param replyNum
     * @param replyText
     * @param boardNum
     * @return
     */
    @GetMapping("/replyUpdate")
    @ResponseBody
    public List<ReplyDTO> replyUpdate(@RequestParam(name = "replyNum")Long replyNum,
                                @RequestParam(name = "replyText")String replyText,
                                @RequestParam(name="boardNum")Long boardNum) {
        if(replyService.update(replyNum,replyText)){
            List<ReplyDTO> replyList = replyService.replyAll(boardNum);
            return replyList;
        }
        return null;
    }

    @GetMapping("/incrementFavorite")
    @ResponseBody
    public int incrementFavorite(@RequestParam(name = "replyNum") Long replyNum,
                            @RequestParam(name="count") int count) {
        int like = replyService.incrementFavorite(replyNum,count);
        return like;
    }
    
    
    
}
