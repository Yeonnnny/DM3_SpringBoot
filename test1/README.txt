[문제]

1) test1 프로젝트를 작성하시오
	> 포트번호는 8889로 설정
2) 정수 x와 y를 폼으로 입력받아 서버로 전송함
	> Controller에서는 전송받은 x,y를 더해서 그 결과를 로그로 출력
3) 생성파일 
	- MainController.java
	- CalculateController.java
	- index.html : 사용자가 입력할 폼 (x, y의 입력상자)
	- result.html : 계산이 잘 되었음을 알리는 "계산 완료" 출력

	  v2) result.html 수정 : (thymeleaf를 포함시켜 서버에서 x, y, x+y) 결과를 받아 출력
	 	  CalculateController.java 수정 : (Model에  x, y, x+y)를 담아서 response 하시오
