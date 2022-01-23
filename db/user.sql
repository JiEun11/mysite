-- 회원가입 sql query
insert into user values(null, '관리자', 'admin@mysite.com', '1234','female',now());

-- 로그인 sql query
select no,name from user where email='admin@mysite.com' and password='1234';


-- update profile sql query
update user set name='...', gender='...' where no=10; -- password 수정 안 할 때
update user set name='...', gender='...', password='...' where no=10; --- password 수정 할 


select * from user;

-- delete user 
DELETE from user where no=2;
 

desc `user`;