drop table guestbook;
drop sequence geust_seq;

create table guestbook(
    guest_seq number primary key,
    guest_name varchar2(50) not null,
    guest_pwd varchar2(20) not null,
    guest_text varchar2(2000),
    regdate date default sysdate
);

create sequence geust_seq;

select * from guestbook;