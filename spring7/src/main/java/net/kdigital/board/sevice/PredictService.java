package net.kdigital.board.sevice;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.board.dto.Iris;

@RequiredArgsConstructor
@Service
@Slf4j
public class PredictService {
	@Value("${iris.predict.server}")
	String url;
	private final RestTemplate restTemplate;
	
	public Map<String, Object> predictRest(Iris iris) {
		log.info("Spring ===> Iris정보: {}", iris.toString() );
		log.info("FastAPI 서버 URL: {}", url );
		
		Map<String, Object> error = new HashMap<>();	// 에러 담을 맵
		Map<String, Object> result = new HashMap<>();	// 정상 데이터를 담을 맵
		
		try {
			// 헤더 준비
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			
			// POST 전송 시 RestTemplate의 postForEntity() 메소드를 사용
			// (1: URL, 2: 요청 바디에 포함되는 값, 3:응답 바디로 받을 타입)
			ResponseEntity<Map> response = restTemplate.postForEntity(url, iris, Map.class);
			log.info("{}", response.getStatusCode());
			result = response.getBody();
			log.info("result {} ", result.get("predict_result"));
			
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			error.put("statusCode", e.getStatusCode()+"");
			error.put("body", e.getStatusText());
			
			log.info("{}", error.get("body"));
			return error;	// error 반환
		} 
		
		return result; // 정상반환
	}
}
