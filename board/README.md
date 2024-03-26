2024년 3월 12일 화요일


# 회원 전용 게시판

## [ 파일 첨부 시 작업]
1) application.properties
    - 디렉토리 설정 
    - 업로드 파일 용량 제한
2) boardWrite.html
    - form태그의 옵션 설정 : enctype="multipart/form-data" 추가
    - input 태그의 name 값 설정/확인

3) BoardDTO.java에서 파일을 받을 수 있도록 처리
    - MultipartFile
    - 이름 추출 (originalFileName/savedFileName)



## [selectAll 함수 변경]

1) 검색어 카테고리, 검색어 파라미터 추가
- 검색이 여러 개면 쿼리문도 여러 개여야 함

글 제목으로 검색한다면 
SQL>
select * 
form board 
where boardTitle like '%'|| searchWord ||'%';

글 작성자로 검색한다면 
SQL>
select * 
form board 
where boardWriter like '%'|| searchWord ||'%';

글 내용으로 검색한다면 
SQL>
select * 
form board 
where boardContent like '%'|| searchWord ||'%';

※ 공백을 입력한 경우 -> 모든 문자열
where boardContent like '%'||""||'%'; -> '%%'


2) repository 변경
- findAll() ==> 전체 조회 (기본 제공)
- findBy컬럼명Containing 메소드 생성 -> 위의 select절 구동
    - ex) findByBoardWriterContaining(searchWord)
        ==> select * 
            from board
            where boardWriter like '%'||searchWord||'%';
    - ex) findByBoardTitleContaining(searchWord)
    - ex) findByBoardContentContaining(searchWord)



## [페이징 처리]
- pageLimit = 10
- page : 사용자가 요청한 페이지 번호

ex) 총 데이터 수  : 156이라면 (반환)..
pageLimit -> 한 페이지당 10개 (설정한 값)
페이지 그룹 : 10 (반환되는 값)

총 페이지수는 ? 16개
1페이지 : 1~10
2페이지 : 11~20
3페이지 : 21~30
...

0그룹 : 1~10
1그룹 : 11~20
...




# 테이블
board(1) : reply(n)


# [댓글 수정 방법] --> 회원 가입 완료 후 다시 와서 할 예정
1. 수정버튼 클릭 -> 댓글 번호를 이용해 selectOne을 실시
                 -> 댓글 입력창에 출력 
                 -> 댓글 작성 버튼을 댓글 수정 버튼으로 바꿈
                    (댓글 작성시에는 댓글 수정 버튼은 disabled) 
                    (댓글 수정시에는 댓글 작성 버튼은 disabled) 
2. 댓글 수정버튼 클릭 
                 -> DB에서 댓글 update 실시
                 -> 업데이트된 댓글을 화면에 출력


# [회원에 관련된 로직]
- 회원가입
- 로그인
- 로그아웃
- 개인정보 수정

1) Dependency 추가
    - 시큐리티
    - 타임리프 시큐리티
2) 인증/인가
    - 인증 : 회원인지 아닌지 구별하는 용도 (아이디와 비밀번호)
    - 인가 : 인증을 받고, 접근하는 페이지에 차별을 두는 것
3) 설정 생성(파일이 있어야 함) - SecurityConfig.java
    - 인증절차가 필요한 
    - 로그인화면을 작성한 후 설정파일에 등록해야 함



Security 시 필요한 클래스 생성

DTO : LoginUserDetails 구현 (implements UserDetails)
Service : UserDetailsService 구현


# [Session]
이동한 페이지로 JSESSIONID를 넘김-> user의 활동(로그인 정보)을 추적할 수 있음 

http의 특징 : stateless 

- 세션? 사이트 방문하는 동안에는 유효한 키
- 객체로서 관리하고 싶으면 메모리에 저장
- 로그아웃 시에 세션 삭제

세선 : 로그인해서 그 페이지에 있는 동안의 정보를 저장해놓는 곳
쿠키 : 한번이라도 사이트에 들어가면 페이지에서 사용자의 로컬에 저장해놓는 곳


