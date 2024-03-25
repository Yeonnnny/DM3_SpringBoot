package net.kdigital.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.board.dto.UserDTO;
import net.kdigital.board.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



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
        userService.insertUser(userDTO);
        
        return "redirect:/";
    }
    
    /**
     * 로그인을 위한 화면 요청 --> 주의) 로그인 처리는 Controller에 넣지 않는다.
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

}
