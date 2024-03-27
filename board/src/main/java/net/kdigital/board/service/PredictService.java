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


@Service
@RequiredArgsConstructor
@Slf4j
public class PredictService {
    private final RestTemplate restTemplate;

    @Value("${iris.predict.server}")
    String url;

    public Map<String, String> predictRest(Iris iris) {
        Map<String,String> error = new HashMap<>();    //에러를 담을 맵
        Map<String,String> result = null;   // 정상결과를 담을 맵
        try {
            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            ResponseEntity<Map> response = restTemplate.postForEntity(url, iris, Map.class);
            result = response.getBody();

        } catch (Exception e) {
            error.put("statusCode", "450");
            error.put("body", "오류났어요");
            return error;
        }
        return result; 
    }


    
}
