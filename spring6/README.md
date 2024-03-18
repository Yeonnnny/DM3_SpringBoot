# 2024년 03월 07일

### Dependencies : jpa / lombok / oracle / thymeleaf / web / devtools

## [학습] 

1) 클라이언트로부터 받은 데이터를 DB에 CRUD

2) 요청 URL의 종류
<pre>
	/ 			첫요청 		(GET) - MainController
	/insert		화면요청 	(GET) - FriendController (데이터 입력을 위한 화면 요청)
	/insert		저장		(POST)- FriendController (DB에 저장하기 위한 요청)
	/selectOne	조회요청	(GET) - FriendController (1명 조회 by 시퀀스번호)
	/list		목록요청	(GET) - FriendController (전체 조회)
	/update		화면요청	(GET) - FriendController (데이터 수정을 위한 화면 요청)
									DB에서 수정하고자 하는 데이터를 조회한 후에 화면에 출력
	/selectOne 	조회 (1명)
	/updateProc 수정처리요청 (POST) - FriendController
	/deleteOne	1명 삭제 (GET) - FriendController(데이터 삭제 요청)
</pre>

3) 추가 작업
	- findAll() 시 Sort하기  - 파라미터 추가
	- 수정 요청 시 - true/false 값을 화면에 반영
	- 유효성 검증
		(1) js로 유효성 검증하는 방법
		(2) Spring에서 제공하는 Validate를 이용하는 방법 - Annotation으로 처리
			a. dependency 추가 
			b. DTO에서 검증 annotation 및 메시지 추가
			c. controller에서 오류 발생 시 해당 오류를 담을 객체(Bind처리)가 필요 
			d. 검증 시 오류가 발생한 경우에는 View 단에 그 사실을 알리고 다시 입력받음
			e. 오류가 없으면 저장 및 수정 작업 실시

	- upateProc에서 JPQL로 하지 않고 예전 방식으로 변경


※ 참고 : Builder 패턴으로 객체 생성하는 방법
	- lombok에서 제공하는 @Builder를 이용하여 생성



























































