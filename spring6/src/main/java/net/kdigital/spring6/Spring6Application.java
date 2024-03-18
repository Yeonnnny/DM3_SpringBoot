package net.kdigital.spring6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.kdigital.spring6.dto.Sample;

@SpringBootApplication
public class Spring6Application {

	public static void main(String[] args) {
		SpringApplication.run(Spring6Application.class, args);
/*		
		// 1) 방식으로 생성(기본 생성자로)
		Sample s1 = new Sample();
		s1.setSeq(1L);
		s1.setUserid("홍길동");
		s1.setPwd("1234");
		s1.setContent("목요일입니다.");
		System.out.println(s1);
		
		// 2) 두 번째 방식으로 생성 (오버로드된 생성자로)
		//	같은 데이터 타입이 있을 때에는 주의
		Sample s2 = new Sample(2L, "전우치", "1111", "금나와라 뚝딱");
		System.out.println(s2);
		
		// 3) 세 번째 방식으로 생성 (Builder 패턴으로 생성)
		Sample s3 = Sample.builder()
					.seq(3L)
					.userid("손오공")
					.content("안녕하시렵니까?") // 순서 바뀌어도 됨
					.pwd("456")
					.build();
		
		System.out.println(s3);
		
		// 4) 네 번째 방식으로 생성 (클래스 내부에 선언한 Builder로 생성)
		Sample s4 = Sample.builder()
				.userid("사오정")
				.content("내일은 금요일")
				.build();
		System.out.println(s4);
		
*/			
		
	}
}
