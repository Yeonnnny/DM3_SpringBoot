2024년 3월 8일

## [실습] 방명록 작성하기 Guestbook

### 주요 기능
1. 방명록 글 쓰기  
2. 방명록 보기
3. 방명록 삭제


### 주요 Request
	/ 			    첫요청 		(GET) - MainController
	/insert		    화면요청 	(GET) - GuestbookController (데이터 입력을 위한 화면 요청)
	/insert		    저장		(POST)- GuestbookController (DB에 저장하기 위한 요청)
	/list	    	목록요청	 (GET) - GuestbookController (전체 조회)
    /deleteOne      삭제요청     (GET) - GuestbookController (글 한개 삭제) - 비밀번호를 입력받아 같은 경우에만 삭제 (Javascript의 prompt() 사용)


기능 구현 step

1.  package (5개) 작성
    - controller
    - service
    - dto
    - entity
    - repository

2. application.properties 수정
    - 포트번호 : 8888
    - context-path : /test3

3. 코드 작성
    - MainController.java
    - index.ftml
        방명록 쓰기
        방명록 전체조회
    - GuestbookController.java



3) 추가 작업
	- findAll() 시 Sort하기  - 파라미터 추가
	- 유효성 검증
		(1) js로 유효성 검증하는 방법
		(2) Spring에서 제공하는 Validate를 이용하는 방법 - Annotation으로 처리
			a. dependency 추가 
			b. DTO에서 검증 annotation 및 메시지 추가
			c. controller에서 오류 발생 시 해당 오류를 담을 객체(Bind처리)가 필요 
			d. 검증 시 오류가 발생한 경우에는 View 단에 그 사실을 알리고 다시 입력받음
			e. 오류가 없으면 저장 및 수정 작업 실시

	- upateProc에서 JPQL로 하지 않고 예전 방식으로 변경
