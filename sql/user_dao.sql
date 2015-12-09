
--회원가입
insert
  into member
values ( member_no_seq.nextval, '안대혁', 'kickscar@gmail.com', '1234', 'male' );

-- commit
commit;


select * from member;

-- login
select no, name, email
  from member
 where email='kickscar@gmail.com'
   and password='1234';