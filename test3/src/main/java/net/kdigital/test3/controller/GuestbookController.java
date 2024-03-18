package net.kdigital.test3.controller;

import java.time.LocalDate;
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
import net.kdigital.test3.dto.GuestbookDTO;
import net.kdigital.test3.service.GuestbookService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService service;

    // public GuestbookController(GuestbookService service) {
    // this.service = service;
    // }

    @GetMapping("/insert")
    public String insert(Model model) {
        model.addAttribute("guestbookDTO", new GuestbookDTO());
        return "insertForm";
    }

    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute GuestbookDTO guestbookDTO, BindingResult bindingResult) {
        // 시간 변경
        guestbookDTO.setRegdate(LocalDate.now());

        log.info("{}", guestbookDTO.toString());
        log.info("bindingResult : {}", bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("에러 발생!!");
            return "insertForm";
        }

        service.insert(guestbookDTO);
        return "redirect:/"; // 작성완료하면 첫 화면으로
    }

    @GetMapping("/list")
    public String list(Model model) {

        List<GuestbookDTO> guestList = service.selectAll();

        model.addAttribute("list", guestList);

        return "list";
    }

    @GetMapping("/deleteOne")
    public String deleteOne(@RequestParam(name = "guestSeq", defaultValue = "0") Long guestSeq) {

        service.deleteOne(guestSeq);
        return "redirect:/list";
    }

    @GetMapping("/updateOne")
    public String updateOne(@RequestParam(name = "guestSeq", defaultValue = "0") Long guestSeq, Model model) {

        GuestbookDTO guestbookDTO = service.selectOne(guestSeq);
        model.addAttribute("guestbookDTO", guestbookDTO);

        return "updateForm";
    }

    @PostMapping("/updateProc")
    public String updateOne(@Valid @ModelAttribute GuestbookDTO guestbookDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "updateForm";
        }

        service.updateOnde(guestbookDTO);

        return "redirect:/list";
    }
}
