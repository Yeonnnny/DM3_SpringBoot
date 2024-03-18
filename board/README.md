2024년 3월 12일 화요일


- 회원 전용 게시판

-[ 파일 첨부 시 작업]
1) application.properties
    - 디렉토리 설정 
    - 업로드 파일 용량 제한
2) boardWrite.html
    - form태그의 옵션 설정 : enctype="multipart/form-data" 추가
    - input 태그의 name 값 설정/확인

3) BoardDTO.java에서 파일을 받을 수 있도록 처리
    - MultipartFile
    - 이름 추출 (originalFileName/savedFileName)



[selectAll 함수 변경]

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



[페이징 처리]
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
