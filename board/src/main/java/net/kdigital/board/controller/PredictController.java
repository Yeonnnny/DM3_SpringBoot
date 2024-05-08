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
import net.kdigital.board.dto.Product;
import net.kdigital.board.service.PredictService;


@Slf4j
@Controller
@RequiredArgsConstructor
public class PredictController {
    private final PredictService predictService;
    
    /**
     * 붓꽃 정보예측을 위한 화면 요청
     * @return
     */
    @GetMapping("/predict")
    public String predict() {
        return "predict/iris";
    }

    /**
     * 붓꽃 정보예측을 위한 화면 요청
     * @return
     */
    @PostMapping("/predict")
    @ResponseBody
    public Map<String,String> predict(@ModelAttribute Iris iris) {
        Map<String, String> result = predictService.predictRest(iris);
        return result;
    }


    @GetMapping("/predict/weird")
    public String weird() {
        return "predict/weird";
    }
    
    @PostMapping("/predict/weird")
    @ResponseBody
    public Map<String, String> weird(@ModelAttribute Product product) {
        log.info("============ 지금 컨트롤러야 {}",product.toString());
        Map<String, String> result = predictService.predictRest(product);
        return result;
    }
    

    
}
