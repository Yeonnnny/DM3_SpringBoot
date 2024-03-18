DTO 패키지 생성
- setter와 toString만 있어도 값을 받을 수 있음
- 기본생성자가 필요한데.. 따로 생성하지 않으면 JVM이 자동으로 만들어주기 때문에 위 두개만 만들주면 됨
- setter : 받을때 필요
- getter : 보낼때 필요


2024년 3월 5일
[학습내용]
- Controller 단에서 객체(FriendDT)나 파일이 첨부되어 전송될 때 사용하는 애노테이션 @ModelAttribute
- 메소드의 매개변수 위치에서 사용

public String temp (@ModelAttribute FriendDTO friendDTO){
	
}

- 클라이언트에서 서버로 데이터를 전송할 때 위와같이 객체로 받을 경우 기본생성자가 호출됨
- 그리고 값을 넣을 때에는 Setter가 자동 호출되므로 DTO객체를 이용해 데이터를 받을 때에는 반드시 기본생성자, Setter가 필요

- 서버에서 클라이언트로 데이터를 보낼 때 Model 객체가 필요

public String temp (@ModelAttribute FriendDTO friendDTO, Model model){
	model.addAttribute("변수명", 값);	
}

- html에서 타임리프로 데이터를 꺼낼 때에는 Getter가 필요




