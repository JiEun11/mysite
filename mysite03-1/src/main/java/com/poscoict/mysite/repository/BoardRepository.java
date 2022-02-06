package com.poscoict.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(BoardVo boardVo) {
		return sqlSession.insert("board.insert", boardVo);
	}
	
	public List<BoardVo> findAllByPageAndKeword(String keyword, Integer page, Integer size) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("startIndex", (page-1)*size);
		map.put("size", size);
		return sqlSession.selectList("board.findAllByPageAndKeyword",map);
	}

	
	public int update(BoardVo boardVo) {
		return sqlSession.update("board.update", boardVo);
//		int result = 0;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			conn = getConnection();
//			
//			String sql = "update board set title=?, contents=? where no=? and user_no=?";
//			pstmt = conn.prepareStatement(sql);
//			
//			pstmt.setString(1, boardVo.getTitle());
//			pstmt.setString(2, boardVo.getContents());
//			pstmt.setLong(3, boardVo.getNo());
//			pstmt.setLong(4, boardVo.getUserNo());
//			
//			result = pstmt.executeUpdate();
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		} finally {
//			try {
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
	
	public int delete(Long no, Long userNo) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("userNo", userNo);
		
		return sqlSession.delete("board.delete",map);
//		
//		int result = 0;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			conn = getConnection();
//			
//			String sql = "delete from board where no = ? and user_no = ?";
//			pstmt = conn.prepareStatement(sql);
//			
//			pstmt.setLong(1, no);
//			pstmt.setLong(2, userNo);
//			
//			result = pstmt.executeUpdate();
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		} finally {
//			try {
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

	public BoardVo findByNo(Long no) {
		return sqlSession.selectOne("board.findByNo", no);
		
//		
//		BoardVo result = null;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//				
//		try {
//			conn = getConnection();
//
//			String sql = "select no, title, contents, g_no as groupNo, o_no as orderNo, depth, user_no as userNo from board where no = ?";
//			pstmt = conn.prepareStatement(sql);
//				
//			pstmt.setLong(1, no);
//			
//			rs = pstmt.executeQuery();
//			if(rs.next()) {
//				result = new BoardVo();
//				
//				result.setNo(rs.getLong(1));
//				result.setTitle(rs.getString(2));
//				result.setContents(rs.getString(3));
//				result.setGroupNo(rs.getInt(4));
//				result.setOrderNo(rs.getInt(5));
//				result.setDepth(rs.getInt(6));
//				result.setUserNo(rs.getLong(7));
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
	
	public BoardVo findByNoAndUserNo(Long no, Long userNo) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("userNo", userNo);
		
		return sqlSession.selectOne("board.findByNoAndUserNo",map);
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

	public int updateHit(Long no) {
		
		return sqlSession.update("board.updateHit", no);
//		int result = 0;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			conn = getConnection();
//			
//			String sql = "update board set hit = hit + 1 where no=?";
//			pstmt = conn.prepareStatement(sql);
//			
//			pstmt.setLong(1, no);
//			
//			result = pstmt.executeUpdate();
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		} finally {
//			try {
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
	
	public int updateOrderNo(Integer groupNo, Integer orderNo) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("groupNo", groupNo);
		map.put("orderNo", orderNo);
		
		return sqlSession.update("board.updateOrderNo", map);
//		int result = 0;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			conn = getConnection();
//			
//			String sql = "update board set o_no = o_no + 1 where g_no = ? and o_no >= ?";
//			pstmt = conn.prepareStatement(sql);
//			
//			pstmt.setInt(1, groupNo);
//			pstmt.setInt(2, orderNo);
//			
//			result = pstmt.executeUpdate();
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		} finally {
//			try {
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
	
	public int getTotalCount(String keyword) {
		return sqlSession.selectOne("board.totalCount", keyword);
	}
	
//	private Connection getConnection() throws SQLException {
//		Connection conn = null;
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			String url = "jdbc:mysql://localhost:3306/webdb?characterEncoding=UTF-8&serverTimezone=UTC";
//			conn = DriverManager.getConnection(url, "webdb", "webdb");
//		} catch (ClassNotFoundException e) {
//			System.out.println("드라이버 로딩 실패:" + e);
//		} 
//		
//		return conn;
//	}	
}