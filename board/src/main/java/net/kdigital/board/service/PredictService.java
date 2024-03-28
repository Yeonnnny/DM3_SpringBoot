package net.kdigital.board.service;


import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.board.dto.Iris;
import net.kdigital.board.dto.Product;


@Service
@RequiredArgsConstructor
@Slf4j
public class PredictService {
    private final RestTemplate restTemplate;

    @Value("${iris.predict.server}")
    String irisUrl;

    @Value("${weird.predict.server}")
    String productUrl;

    public Map<String, String> predictRest(Iris iris) {
        Map<String,String> error = new HashMap<>();    //에러를 담을 맵
        Map<String,String> result = null;   // 정상결과를 담을 맵
        try {
            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            ResponseEntity<Map> response = restTemplate.postForEntity(irisUrl, iris, Map.class);
            result = response.getBody();

        } catch (Exception e) {
            error.put("statusCode", "450");
            error.put("body", "오류났어요");
            return error;
        }
        return result; 
    }

    // 오버로딩
    public Map<String, String> predictRest(Product product) {
        Map<String, String> error = new HashMap<>();
        Map<String, String> result = null;
        
        log.info("============ 지금 서비스야 {}", product.toString());
        
        try {
            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            log.info("============ 1 : {}", restTemplate);
            
            ResponseEntity<Map> response = restTemplate.postForEntity(productUrl, product, Map.class);
            
            log.info("============== 2 : ddd");
            
            result = response.getBody();

        } catch (Exception e) {
            error.put("StatusCode", "450");
            error.put("body", "오류발생!");
            return error;
        }

        return result;
    }


    
}
