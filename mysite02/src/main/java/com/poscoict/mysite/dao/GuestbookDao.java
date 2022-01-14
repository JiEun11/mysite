package com.poscoict.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscoict.mysite.vo.GuestbookVo;


public class GuestbookDao {
	
	public List<GuestbookVo> findAll(){
		List<GuestbookVo> result = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			// 3. SQL준비
			String sql = "SELECT no,"
					+ "	name, "
					+ " password,"
					+ "	message,"
					+ "	DATE_FORMAT(reg_date, \"%Y/%m/%d %H:%i:%s\") AS reg_date"
					+ "	FROM guestbook "
					+ "ORDER BY reg_date DESC";
			pstmt = conn.prepareStatement(sql);
			
			// 5. SQL 실행
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String message = rs.getString(4);
				String date = rs.getString(5);
				
				GuestbookVo gbvo = new GuestbookVo();
				gbvo.setNo(no);
				gbvo.setName(name);
				gbvo.setPassword(password);
				gbvo.setRegDate(date);
				gbvo.setMessage(message);
				
				result.add(gbvo);
			}
		} catch (SQLException e) {
			System.out.println("error: " +e );
		} finally {
			// 자원 정리
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean insert(GuestbookVo gbvo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			//3. SQL준비
			String sql = "INSERT INTO guestbook VALUES(null, ?, ?, ?, now())";
			pstmt = conn.prepareStatement(sql);
			
			//4. 바인딩
			pstmt.setString(1, gbvo.getName());
			pstmt.setString(2, gbvo.getPassword());
			pstmt.setString(3, gbvo.getMessage());
			
			//5. SQL실행
			int count = pstmt.executeUpdate();
			result = count == 1; //count==1이면 true, count==1이 아니면 false
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public boolean delete(int no, String password) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			// 3. SQL 준비
			String sql = "DELETE FROM guestbook WHERE no = ? AND password=? ";
			pstmt = conn.prepareStatement(sql);
			
			// 4. 바인딩
			pstmt.setInt(1, no);
			pstmt.setString(2, password);
			
			// 5. SQL 실행
			int count = pstmt.executeUpdate();
			result = count==1;
			
		} catch (SQLException e) {
			System.out.println("error: " +e);
		} finally {
			//자원 정리
			try {
				if(rs!=null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
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
