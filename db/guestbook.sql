show tables;
desc guestbook;

-- INSERT 
INSERT INTO guestbook VALUES(null, '김지은', '1234', 'hello', now());
INSERT INTO guestbook VALUES(null, '김지수', '1234', '황', now());

-- SELECT 
SELECT * FROM guestbook;

SELECT no,
	name,
	DATE_FORMAT(reg_date, "%Y/%m/%d %H:%i:%s") AS reg_date, 
	message 
	FROM guestbook 
ORDER BY reg_date DESC; 

-- DELETE 
DELETE FROM guestbook WHERE no = 7 AND password='1111';
