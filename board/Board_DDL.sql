-- 2024/03/12
-- 회원 전용 게시판 테이블


-- 1) 회원 테이블
drop table boarduser;

create table boarduser(
    user_id varchar2(50) primary key,
    user_name varchar2(50) not null,
    user_pwd varchar2(100) not null,
    email varchar2(40) not null,
    roles varchar2(20) default 'ROLE_USER',     -- 'ROLE_USER',"ROLE_ADMIN" 등
    enabled char(1) default '1' check(enabled in ('1','0'))    -- 사용자가 활겅화된 상태 여부 파익
);

select * from boarduser;


-- 2) 게시판 테이블
drop table board;
drop SEQUENCE board_seq;

create table board(
    board_num           number constraints board_num_pk primary key,
    board_writer        varchar2(20) constraints board_writer_nn not null,
    board_title         varchar2(20) default '제목없음',
    board_content       varchar2(4000),
    hit_count           number default 0,
    favorite_count      number default 0,
    create_date         date default sysdate,
    update_date         date
);

create SEQUENCE board_seq;

--board_title 문자열 길이를 200으로 변경함
alter table board modify board_title varchar2(200); 

-- 첨부 파일에 대한 컬럼 추가
alter table board add originalFileName varchar2(200);
alter table board add savedFileName varchar2(200);

-- 첨부 파일에 대한 컬럼명 변경
ALTER TABLE board RENAME COLUMN originalFileName TO original_file_name;
ALTER TABLE board RENAME COLUMN savedFileName TO saved_file_name;


select * from board;


-- 3) 댓글 테이블
drop table reply;
drop sequence reply_seq;

create table reply(
    reply_num number primary key, -- 댓글 번호
    board_num number references board(board_num) on delete cascade, --게시글 번호
    reply_writer varchar2(20), -- 댓글 작성자
    reply_text varchar2(1000), -- 댓글 내용
    create_date date default sysdate, -- 댓글 작성일
    favorite number default 0-- 댓글 좋아요(공감)
);

create sequence reply_seq;

select * from reply;












