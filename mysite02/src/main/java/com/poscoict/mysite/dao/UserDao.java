package com.poscoict.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.poscoict.mysite.vo.UserVo;

public class UserDao {
	
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
	
	
	public boolean insert(UserVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			//3. SQL준비
			String sql = "INSERT INTO user VALUES(null, ?, ?, ?, ?, now());";
			pstmt = conn.prepareStatement(sql);
			
			//4. 바인딩
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			
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
	
	public UserVo findByEmailAndPassword(String email, String password) {
		UserVo result = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			//3. SQL준비
			String sql = "select no,name from user where email=? and password=?";
			pstmt = conn.prepareStatement(sql);
			
			//4. 바인딩
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			//5. SQL실행
			rs = pstmt.executeQuery();
			if(rs.next()) {  //여러 개가 아니라 1개 리턴되기 때문에 while이 아닌 if를 써도된다.
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				
				result = new UserVo();
				result.setNo(no);
				result.setName(name);
			}
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


	public UserVo findByNo(Long no) {
		UserVo result = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			//3. SQL준비
			String sql = "select name,email,password,gender from user where no=?";
			pstmt = conn.prepareStatement(sql);
			
			//4. 바인딩
			pstmt.setLong(1, no);
			
			//5. SQL실행
			rs = pstmt.executeQuery();
			if(rs.next()) {  //여러 개가 아니라 1개 리턴되기 때문에 while이 아닌 if를 써도된다.
				String name = rs.getString(1);
				String email = rs.getString(2);
				String password = rs.getString(3);
				String gender = rs.getString(4);
				
				result = new UserVo();
				result.setName(name);
				result.setEmail(email);
				result.setPassword(password);
				result.setGender(gender);
			}
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
	
	public boolean updateUser(UserVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = null;
			
			//3. SQL준비
			if(vo.getPassword() == null) {
				sql = "update user set name=?, gender=? where no=?";
			
			}else {
				sql = "update user set name=?, gender=?, password=? where no=?";
			}
			
			pstmt = conn.prepareStatement(sql);
			
			// 4. 바인딩(binding)
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getGender());
			
			if(vo.getPassword() == null) {
				pstmt.setLong(3, vo.getNo());
			}
			else {
				pstmt.setString(3, vo.getPassword());
				pstmt.setLong(4, vo.getNo());
			}
			
			// 5. sql 실행, executeQuery는 rs, executeUpdatesms int로 반환
			result = (pstmt.executeUpdate() == 1);
			
		}	catch (SQLException e) {
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
		
		
}
