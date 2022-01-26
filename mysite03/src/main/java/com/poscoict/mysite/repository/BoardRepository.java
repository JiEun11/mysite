package com.poscoict.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.PageVo;

@Repository
public class BoardRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	/*
	 * 일단 게시글 다 보여주기 
	 */
	public List<BoardVo> findAll(){
		List<BoardVo> result = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			result = new ArrayList<>();
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
					+ "	DATE_FORMAT(b.reg_date, \"%Y/%m/%d %H:%i:%s\") AS reg_date,"
					+ " a.no"
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
				Long userNo = rs.getLong(10);
				
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
				vo.setUserNo(userNo);
				
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
	 *  검색 & 페이징 적용 listAll() 
	 */
//	public List<BoardVo> findAll(int currentPage, int boardLimit, String tag, String kwd){
		
//		PageVo pageVo = new PageVo();
//		pageVo.setCurrentPage(currentPage);
//		pageVo.setBoardLimit(boardLimit);
//		pageVo.setTag(tag);
//		pageVo.setKwd(kwd);
//		
//		Map<String,Object> map = new HashMap<>();
//		map.put("currentPage", currentPage);
//		map.put("boardLimit", boardLimit);
//		map.put("tag", tag);
//		map.put("kwd", kwd);
//		
//		return sqlSession.selectList("board.findAll", map);
//		return sqlSession.selectList("board.findAll", pageVo);
//	}

	/*
	 *  검색 & 페이징 적용 listAll() 구현 중 
	 */
	public List<BoardVo> findAll(PageVo pageVo){
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("startPage", pageVo.getStartPage());
		map.put("boardLimit", pageVo.getBoardLimit());
		map.put("tag", pageVo.getTag());
		map.put("kwd", pageVo.getKwd());
		System.out.println("tag : "+ pageVo.getTag());
		System.out.println("kwd : "+ pageVo.getKwd());
		return sqlSession.selectList("board.findAll",map);
//		List<BoardVo> result = new ArrayList<>();
//		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			
//			conn = getConnection();
//			// 3. SQL준비
//			String sql = null;
//			if(tag == null || tag.isBlank()==true) {
//				sql = "SELECT b.no,"
//						+ "	b.title,"
//						+ " a.name,"
//						+ "	b.hit,"
//						+ " b.g_no,"
//						+ " b.o_no,"
//						+ " b.depth,"
//						+ " b.contents,"
//						+ "	DATE_FORMAT(b.reg_date, \"%Y/%m/%d %H:%i:%s\") AS reg_date,"
//						+ " a.no"
//						+ "	FROM user a, board b"
//						+ " WHERE  a.no = b.user_no"
//						+ " ORDER BY b.g_no DESC, b.o_no ASC"
//						+ " LIMIT ?, ?";
//				
//				pstmt = conn.prepareStatement(sql);
//				
//				// 4. binding
//				pstmt.setInt(1, startPage);
//				pstmt.setInt(2, boardLimit);
//			}else {
//				sql = "SELECT b.no,"
//						+ "	b.title,"
//						+ " a.name,"
//						+ "	b.hit,"
//						+ " b.g_no,"
//						+ " b.o_no,"
//						+ " b.depth,"
//						+ " b.contents,"
//						+ "	DATE_FORMAT(b.reg_date, \"%Y/%m/%d %H:%i:%s\") AS reg_date,"
//						+ " a.no"
//						+ "	FROM user a, board b"
//						+ " WHERE  a.no = b.user_no AND "+ tag +" LIKE '%" + kwd + "%'"
//						+ " ORDER BY b.g_no DESC, b.o_no ASC"
//						+ " LIMIT ?, ?";
//				
//				pstmt = conn.prepareStatement(sql);
//				
//				// 4. binding
//				pstmt.setInt(1, startPage);
//				pstmt.setInt(2, boardLimit);
//			}
//			
//			// 5. SQL 실행
//			rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				Long no = rs.getLong(1);
//				String title = rs.getString(2);
//				String userName = rs.getString(3);
//				int hit = rs.getInt(4);
//				int groupNo = rs.getInt(5);
//				int orderNo = rs.getInt(6);
//				int depth = rs.getInt(7);
//				String contents = rs.getString(8);
//				String date = rs.getString(9);
//				Long userNo = rs.getLong(10);
//				
//				BoardVo vo = new BoardVo();
//				vo.setNo(no);
//				vo.setTitle(title);
//				vo.setUserName(userName);
//				vo.setHit(hit);
//				vo.setGroupNo(groupNo);
//				vo.setOrderNo(orderNo);
//				vo.setDepth(depth);
//				vo.setContents(contents);				
//				vo.setRegDate(date);
//				vo.setUserNo(userNo);
//				
//				result.add(vo);
//			}
//		} catch (SQLException e) {
//			System.out.println("error: " +e );
//		} finally {
//			// 자원 정리
//			try {
//				if(rs != null) {
//					rs.close();
//				}
//				if(pstmt != null) {
//					pstmt.close();
//				}
//				if(conn != null) {
//					conn.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return result;
	}
	/*
	 * 게시글 카운트 
	 */
	public int boardTotalCnt(String tag, String kwd) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("tag", tag);
		map.put("kwd", kwd);
		return sqlSession.selectOne("board.boardTotalCnt", map);
		// return sqlSession.selectOne("board.boardTotalCnt", "%"+kwd+"%");
	}
	
	/*
	 * 게시글 작성하기
	 */
	public boolean insert(BoardVo vo) {
		int count = sqlSession.insert("board.insert", vo);
		return count == 1;
	}
	
	/*
	 * 상세글 보기  
	 */
	public BoardVo findOne(Long no) {
		BoardVo result = sqlSession.selectOne("board.findOne", no);
		System.out.println(result);
		return result;
	}
	
	
	public BoardVo findByNoAndUserNo(Long no, Long userNo) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("userNo", userNo);
		return sqlSession.selectOne("board.findByNoAndUserNo", map);
//		
//		BoardVo result = null;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//				
//		try {
//			conn = getConnection();
//
//			String sql = "select no, title, contents from board where no = ? and user_no = ?";
//			pstmt = conn.prepareStatement(sql);
//				
//			pstmt.setLong(1, no);
//			pstmt.setLong(2, userNo);
//			
//			rs = pstmt.executeQuery();			
//			if(rs.next()) {
//				result = new BoardVo();
//				
//				result.setNo(rs.getLong(1));
//				result.setTitle(rs.getString(2));
//				result.setContents(rs.getString(3));
//			}
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		} finally {
//			try {
//				if(rs != null) {
//					rs.close();
//				}
//				if(pstmt != null) {
//					pstmt.close();
//				}
//				if(conn != null) {
//					conn.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		return result;
	}
	
	/*
	 * 글 수정 눌렀을 때  
	 */
	public boolean updateBoard(BoardVo vo) {
		int count = sqlSession.update("board.updateBoard",vo);
		return count==1;
//		
//		boolean result = false;
//		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			conn = getConnection();
//			
//			//3. SQL준비
//			String sql = "update board set title=?, contents=? where no=?";			
//			pstmt = conn.prepareStatement(sql);
//			
//			// 4. 바인딩(binding)
//			pstmt.setString(1, vo.getTitle());
//			pstmt.setString(2, vo.getContents());
//			pstmt.setLong(3, vo.getNo());
//			
//			// 5. sql 실행, executeQuery는 rs, executeUpdatesms int로 반환
//			result = (pstmt.executeUpdate() == 1);
//			
//		}	catch (SQLException e) {
//			System.out.println("error: " + e);
//		} finally {
//			// 자원 정리
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (pstmt != null) {
//					pstmt.close();
//				}
//				if (conn != null) {
//					conn.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		return result;
	}
	
	/*
	 * delete 게시글  
	 */
	public boolean deleteBoard(Long no, Long userNo) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("userNo", userNo);
		
		return sqlSession.delete("board.deleteBoard",map)==1;
	}
	
	/*
	 *  답글 작성 시 orderNo 증가 쿼리
	 */
	public boolean updateOrderNo(int orderNo, int groupNo) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderNo);
		map.put("groupNo", groupNo);

		return 1==sqlSession.update("board.updateOrderNo", map);
//		boolean result = false;
//		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			conn = getConnection();
//			
//			//3. SQL준비
//			String sql = "update board set o_no = o_no + 1 where o_no > ?"
//					+ " and g_no = ?";			
//			pstmt = conn.prepareStatement(sql);
//			
//			// 4. 바인딩(binding)
//			pstmt.setInt(1, orderNo);
//			pstmt.setInt(2, groupNo);
//			
//			// 5. sql 실행, executeQuery는 rs, executeUpdatesms int로 반환
//			result = (pstmt.executeUpdate() == 1);
//			
//		}	catch (SQLException e) {
//			System.out.println("error: " + e);
//		} finally {
//			// 자원 정리
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (pstmt != null) {
//					pstmt.close();
//				}
//				if (conn != null) {
//					conn.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		
//		return result;
	}
	
	/*
	 * 조회수 증가 dao 
	 */
	public boolean hitUp(BoardVo vo) {
		int count = sqlSession.update("board.hitUp", vo);
		return count == 1;
//		
//		boolean result = false;
//		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			conn = getConnection();
//			
//			//3. SQL준비
//			String sql = "update board set hit=hit+1 where no=?";			
//			pstmt = conn.prepareStatement(sql);
//			
//			// 4. 바인딩(binding)
//			pstmt.setLong(1, vo.getNo());
//			
//			// 5. sql 실행, executeQuery는 rs, executeUpdatesms int로 반환
//			result = (pstmt.executeUpdate() == 1);
//			
//		}	catch (SQLException e) {
//			System.out.println("error: " + e);
//		} finally {
//			// 자원 정리
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (pstmt != null) {
//					pstmt.close();
//				}
//				if (conn != null) {
//					conn.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		return result;

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
