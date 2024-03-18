package net.kdigital.spring4.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {
	
		@GetMapping({"/",""})
		public String index() {
			return "index";
		}
	
}
