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
					+ " b.g_no,"
					+ " b.o_no,"
					+ " b.depth,"
					+ " b.contents,"
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
				int groupNo = rs.getInt(5);
				int orderNo = rs.getInt(6);
				int depth = rs.getInt(7);
				String contents = rs.getString(8);
				String date = rs.getString(9);
				
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setUserName(userName);
				vo.setHit(hit);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);
				vo.setContents(contents);				
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
			String sql = "";
			//3. SQL준비
			if(vo.getGroupNo()==null) {
				sql = "INSERT INTO board VALUES(null,?,?,?,IFNULL((SELECT MAX(?)+1 FROM board b),1),?,?,now(),?)";
			}else {
				sql = "INSERT INTO board VALUES(null,?,?,?,?,?,?,now(),?)";;
			}
			
			pstmt = conn.prepareStatement(sql);
			
			//4. 바인딩
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getHit());	  
			pstmt.setInt(4, vo.getGroupNo());
			pstmt.setInt(5, vo.getOrderNo());  
			pstmt.setInt(6, vo.getDepth()+1);	   
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
			String sql = "SELECT title, contents, g_no, o_no, depth, user_no, hit "
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
//				int hit = rs.getInt(3);
				int groupNo = rs.getInt(3);
				int orderNo = rs.getInt(4);
				int depth = rs.getInt(5);
				long userNo = rs.getLong(6);
				int hit = rs.getInt(7);
				
				result = new BoardVo();
				result.setNo(no);
				result.setTitle(title);
				result.setContents(content);
//				result.setHit(hit);
				result.setGroupNo(groupNo);
				result.setOrderNo(orderNo);
				result.setDepth(depth);
				result.setUserNo(userNo);
				result.setHit(hit);
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
	 * delete 게시글  
	 */
	public boolean deleteBoard(Long no) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			// 3. SQL 준비
			String sql = "DELETE FROM board WHERE no = ? ";
			pstmt = conn.prepareStatement(sql);
			
			// 4. 바인딩
			pstmt.setLong(1, no);
			
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
	 *  답글 작성 시 orderNo 증가 쿼리
	 */
	public boolean updateOrderNo(int orderNo, int groupNo) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			//3. SQL준비
			String sql = "update board set o_no = o_no + 1 where o_no > ?"
					+ " and g_no = ?";			
			pstmt = conn.prepareStatement(sql);
			
			// 4. 바인딩(binding)
			pstmt.setInt(1, orderNo);
			pstmt.setInt(2, groupNo);
			
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
	 * 조회수 증가 dao 
	 */
	public boolean hitUp(BoardVo vo) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			//3. SQL준비
			String sql = "update board set hit=hit+1 where no=?";			
			pstmt = conn.prepareStatement(sql);
			
			// 4. 바인딩(binding)
			pstmt.setLong(1, vo.getNo());
			
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
