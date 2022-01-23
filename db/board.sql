-- board

desc board;

-- JOIN 확인
SELECT b.no, b.title, a.name, b.hit, b.contents, DATE_FORMAT(b.reg_date, "%Y/%m/%d %H:%i:%s") AS reg_date, a.name 
FROM user a, board b
WHERE  a.no = b.user_no 
ORDER BY b.g_no DESC, b.o_no ASC; 

-- findAll() 
SELECT b.no, b.title, a.name, b.hit, b.g_no , b.o_no , b.depth , b.contents, DATE_FORMAT(b.reg_date, "%Y/%m/%d %H:%i:%s") AS reg_date, a.`no` 
FROM user a, board b
WHERE  a.no = b.user_no 
ORDER BY b.g_no DESC, b.o_no ASC; 

-- insert() : 게시글 작성 hit : 0 / order_no : 1/ depth : 1 
INSERT INTO board VALUES(null,"뭐먹지?","샌드위치",0 ,IFNULL((SELECT MAX(g_no)+1 FROM board b),1),1, depth + 1 ,now(),3);

INSERT INTO board VALUES(null,"뭐먹지?","샌드위치",0 ,IFNULL((SELECT MAX(g_no)+1 FROM board b),1),1, depth + 1 ,now(),3);

SELECT MAX(g_no)+1 FROM board b

select max(g_no)
from board; 

SELECT MAX(NO)+1
FROM board;

-- show board
SELECT * 
FROM board b
ORDER BY b.g_no DESC, b.o_no ASC;

-- findOne( ) : 글 수정 눌렀을 때 전달해줘야하는 title, contents 
SELECT title, contents
FROM board 
WHERE no = 1;

-- updateBoard() : 게시글 UPDATE 
UPDATE board SET contents="맛있겠지?" WHERE no=2; 

-- 게시글 삭제 DELETE
DELETE FROM board where no=5;

-- 조회수 증가 
UPDATE board SET hit = hit + 1 
WHERE no = 8;

-- orderNo 증가 
update board set o_no = o_no + 1 where o_no > ? and g_no = ?;

-- 글 개수 카운트 
SELECT COUNT(*) FROM board WHERE title like "%딸기%";

-- 페이징 
SELECT b.no, b.title, a.name, b.hit, b.g_no , b.o_no , b.depth , b.contents, DATE_FORMAT(b.reg_date, "%Y/%m/%d %H:%i:%s") AS reg_date 
FROM user a, board b
WHERE  a.no = b.user_no AND contents LIKE "%종%"
ORDER BY b.g_no DESC, b.o_no ASC
LIMIT 0, 3;

SELECT b.no, b.title, a.name, b.hit, b.g_no , b.o_no , b.depth , b.contents, DATE_FORMAT(b.reg_date, "%Y/%m/%d %H:%i:%s") AS reg_date
FROM user a, board b
WHERE  a.no = b.user_no AND title LIKE "%%"
ORDER BY b.g_no DESC, b.o_no ASC
LIMIT 3, 3;

SELECT * FROM board WHERE g_no >= 2;

SELECT COUNT(*) 
FROM user a, board b
WHERE a.no = b.user_no AND name LIKE '%김지수%';