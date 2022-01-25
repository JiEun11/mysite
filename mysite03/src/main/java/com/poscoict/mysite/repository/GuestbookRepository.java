package com.poscoict.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestbookVo> findAll(){
		return sqlSession.selectList("guestbook.findAll");
	}
	
	public boolean insert(GuestbookVo vo) {
		int count = sqlSession.insert("guestbook.insert", vo);
		// vo를 넣었는데 insert 일어나고 나서 pk를 알고싶을 때 
		return count==1 ;
		
	}
	
	public boolean delete(Long no, String password) {
		
		Map<String,Object> map = new HashMap<>();
		map.put("no", no);
		map.put("password", password);
		
		return 1 == sqlSession.delete("guestbook.delete",map);
	}
	
	/*
	 * driver loading 
	 */
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			//1. JDBC 드라이버 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//2. 연결하기
			String url = "jdbc:mysql://localhost:3306/webdb?characterEncoding=UTF-8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			
		} catch (ClassNotFoundException e) {
			System.out.println("Fail to load a driver.: " + e);
		}
		
		return conn;
	}
}
