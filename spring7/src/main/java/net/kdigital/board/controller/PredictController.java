package net.kdigital.board.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.board.dto.Iris;
import net.kdigital.board.sevice.PredictService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PredictController {
	
	private final PredictService service;
	
	/**
	 * 붓꽃 정보예측을 위한 화면 요청
	 * @return
	 */
	@GetMapping("/predict")
	public String predict() {
		return "iris";
	}
	
	/**
	 * 붓꽃 정보예측을 위한 화면 요청
	 * @return
	 */
	@PostMapping("/predict")
	@ResponseBody
	public Map<String, Object> predict(@ModelAttribute Iris iris) {
		log.info("{}", iris.toString());

		Map<String, Object> result = service.predictRest(iris);		
		
		return result;
	}
}
