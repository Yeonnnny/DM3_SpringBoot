package net.kdigital.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.board.dto.UserDTO;
import net.kdigital.board.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;

    /**
     * 회원가입을 위한 화면 요청
     * @return
     */
    @GetMapping("/join")
    public String join() {
        return "user/join";
    }

    /**
     * 회원가입 처리 요청
     * @param userDTO
     * @return
     */
    @PostMapping("/joinProc")
    public String joinProc(@ModelAttribute UserDTO userDTO) {
        log.info("{}",userDTO.toString());

        // 롤, 계정 활성화 여부 세팅
        userDTO.setRoles("ROLE_USER");
        userDTO.setEnabled(true);

        userService.joinProc(userDTO);
        
        return "redirect:/";
    }
    
    /**
     * 로그인을 위한 화면 요청 --> 주의) 로그인 처리는 Controller에 넣지 않는다.
     * @return
     */
    @GetMapping("/login")
    public String login(
        @RequestParam(value = "error", required =false) String error,
        @RequestParam(value = "errMessage", required = false) String errMessage,
        Model model
    ) {
        model.addAttribute("error", error);
        model.addAttribute("errMessage", errMessage);
        return "user/login";
    }

    @GetMapping("/confirmId")
    @ResponseBody
    public UserDTO confirmId(@RequestParam(name = "userId") String userId) {
        log.info("------------ userId : {}",userId);
        UserDTO dto = userService.confirmId(userId);
        log.info("------------ dto : {}",dto);
        return dto;
    }
    

}
