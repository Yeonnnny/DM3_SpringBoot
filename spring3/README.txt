
# Project 명 : Spring 3

### 주요 업무
1) 클라이언트의 요청과 함께 전송된 파라미터를 받는 것
2) 요청 종류
/  					Get 요청
/param/send			Get 요청 함께 전송되는 데이터 (name, age) -> a 태그로!
/param/sendForm		Get 요청 함께 전송되는 데이터 (name, age) -> form 태그로!

3) 서버에서 클라이언트로 응답할 때 데이터를 실어보내기 위해 
	- Model 캑체를 요청 (파라미터로 요청 : Spring Framework가 제공)
	- Model 객체의 addAttribute() 메소드를 이용해서  key, value의 쌍으로 넣고
	- Forwarding 방식으로 응답
	
4) 클라이언트 측에서 데이터를 받을 때에는 Thymeleaf가 필요!
	- <html xmlns="http://www.thymeleaf.org"> 설정 후
	- 데이터가 표시될 위치에 [[${key}]] 






------------------------------------------------------------------------------------------

#### defaultValue : 값이 전달 안된 경우에 기본값이 출력되도록 함
@RequestParam(name="name", 	defaultValue = "홍길동") String name,
@RequestParam(name="age", 	defaultValue = "30") int age		

------------------------------------------------------------------------------------------

server에서 client로 보내려면 데이터를 바구니에 담아서 보내야 하는데 
바구니 이름이 Model임 (import org.springframework.ui.Model;)
parameter로 받을 수 있는데 이게 직접 전달하는게 아니라 우리가 Model을 쓰면 Spring프레임워크에서 보내주는 것임

model.addAttribute(key, value) 형식으로 모델에 담음

return "result"; // forwarding방식으로 result.html에서 변수 받음

result.html 의 언어 변경하는 부분에 
<html lang="ko" xmlns:th="http://thymeleaf.org"> 를 넣음
그리고 받아서 쓸때는 
[[${key}]] 로 받아서 씀


