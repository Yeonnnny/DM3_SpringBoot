1) 요청 처리할 Controller 생성
	- MainController.java
	- 클래스명 위에 @Controller
	- 실제 코드는 메소드
		@GetMapping("/")
		public String 메소드명 (Model model){
			// 수행할 작업들...
			return "html파일명"; 
		}