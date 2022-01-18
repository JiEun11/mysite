package com.poscoict.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscoict.mysite.vo.BoardVo;

public class BoardDao {
	
	/*
	 * 일단 게시글 다 보여주기 
	 */
	
	public List<BoardVo> findAll(){
		List<BoardVo> result = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			// 3. SQL준비
			String sql = "SELECT b.no,"
					+ "	b.title,"
					+ " a.name,"
					+ "	b.hit,"
					+ "	DATE_FORMAT(b.reg_date, \"%Y/%m/%d %H:%i:%s\") AS reg_date"
					+ "	FROM user a, board b"
					+ " WHERE  a.no = b.user_no"
					+ " ORDER BY b.g_no DESC, b.o_no ASC";
			pstmt = conn.prepareStatement(sql);
			
			// 5. SQL 실행
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String userName = rs.getString(3);
				int hit = rs.getInt(4);
				String date = rs.getString(5);
				
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setUserName(userName);
				vo.setHit(hit);
				vo.setRegDate(date);
				
//				System.out.println(vo.toString());
				result.add(vo);
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
		
	/*
	 * 게시글 작성하기
	 */
	public boolean insert(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			//3. SQL준비
			String sql = "INSERT INTO board VALUES(null,?,?,?,IFNULL((SELECT MAX(?)+1 FROM board b),1),?,?,now(),?)";
			pstmt = conn.prepareStatement(sql);
			
			//4. 바인딩
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getHit());	  
			pstmt.setInt(4, vo.getGroupNo());
			pstmt.setInt(5, vo.getOrderNo());  
			pstmt.setInt(6, vo.getDepth());	   
			pstmt.setLong(7, vo.getUserNo());
			
			//5. SQL실행
			int count = pstmt.executeUpdate();
			result = count == 1; //count==1이면 true, count==1이 아니면 false
			System.out.println(vo.toString());
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
	
	/*
	 * 상세글 보기  
	 */
	public BoardVo findOne(Long no) {
		BoardVo result = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			//3. SQL준비
			String sql = "SELECT title, contents "
					+ "FROM board "
					+ "WHERE no = ?";
			pstmt = conn.prepareStatement(sql);
			
			//4. 바인딩
			pstmt.setLong(1, no);
			
			//5. SQL실행
			rs = pstmt.executeQuery();
			if(rs.next()) {  //여러 개가 아니라 1개 리턴되기 때문에 while이 아닌 if를 써도된다.
				String title = rs.getString(1);
				String content = rs.getString(2);
				
				result = new BoardVo();
				result.setNo(no);
				result.setTitle(title);
				result.setContents(content);
			
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
	
	/*
	 * 글 수정 눌렀을 때  
	 */
	public boolean updateBoard(BoardVo vo) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			//3. SQL준비
			String sql = "update board set title=?, contents=? where no=?";			
			pstmt = conn.prepareStatement(sql);
			
			// 4. 바인딩(binding)
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());
			
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
