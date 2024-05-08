-- 2024년 3월 12일
-- 회원전용 게시판 테이블

-- 1) 회원 테이블
DROP TABLE boarduser;
CREATE TABLE boarduser
(
    user_id      VARCHAR2(50) PRIMARY KEY
    , user_name  VARCHAR2(50) NOT NULL
    , user_pwd   VARCHAR2(100) NOT NULL
    , email      VARCHAR2(40) NOT NULL
    , roles      VARCHAR2(20) DEFAULT 'ROLE_USER'     -- 'ROLE_USER', 'ROLE_ADMIN' 등
    , enabled    CHAR(1) DEFAULT '1' CHECK(enabled IN ('1', '0'))  -- 사용자가 활성화된 상태인지 여부
);


-- 2) 게시판 테이블
DROP TABLE board;
DROP SEQUENCE board_seq;

CREATE TABLE board
(
    board_num			NUMBER constraint board_seq PRIMARY KEY
    , board_writer      VARCHAR2(20) constraint board_writer NOT NULL
    , board_title       VARCHAR2(200) default '제목없음'
    , board_content   	VARCHAR2(4000)
    , hit_count         NUMBER default 0
    , favorite_count   	NUMBER default 0
    , create_date       DATE default sysdate
    , update_date       DATE 
);

CREATE SEQUENCE board_seq; 

-- 첨부파일을 위한 컬럼 추가
ALTER TABLE board ADD original_file_name VARCHAR2(200);
ALTER TABLE board ADD saved_file_name VARCHAR2(200);

-- 1) 컬럼 드랍
-- ALTER TABLE board DROP COLUMN originalFileName ;
-- ALTER TABLE board DROP COLUMN savedFileName ;

update board
set saved_file_name = ''
where board_num=21;

SELECT * FROM board;
commit;

-- 3) 댓글 테이블
DROP TABLE reply;
DROP SEQUENCE reply_seq;

CREATE TABLE reply
(
    reply_num       NUMBER PRIMARY KEY      								-- 댓글번호
    , board_num     NUMBER REFERENCES board(board_num) ON DELETE CASCADE    -- 게시글 번호
    , reply_writer  VARCHAR2(20)                           					-- 댓글 작성자
    , reply_text    VARCHAR2(1000) NOT NULL      							-- 댓글 내용
    , create_date   DATE DEFAULT sysdate           							-- 댓글 작성일
);

CREATE SEQUENCE reply_seq;
