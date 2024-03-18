drop table friend;
drop SEQUENCE friend_seq;

create table friend
(
    friend_seq number primary key,
    fname varchar2(30) not null,
    age number(3) default 1,
    phone varchar2(20) unique,
    birthday date default sysdate,
    active char(1) default '1'
);

create sequence friend_seq;

select * from friend;