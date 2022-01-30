package com.poscoict.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.mysite.vo.UserVo;

@Repository
public class UserRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public boolean updateUser(UserVo userVo) {
		int count = sqlSession.update("user.updateUser",userVo);
		return count == 1;
	}
	
	public boolean insert(UserVo userVo) {
		int count = sqlSession.insert("user.insert", userVo);
		return count == 1;
	}
	
	public UserVo findByNo(Long userNo) {
		return sqlSession.selectOne("user.findByNo", userNo);
//		UserVo result = null;
//		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			conn = getConnection();
//			
//			//3. SQL준비
//			String sql = "select no, name, email, gender from user where no=?";
//			pstmt = conn.prepareStatement(sql);
//			
//			//4. 바인딩
//			pstmt.setLong(1, userNo);
//			
//			//5. SQL실행
//			rs = pstmt.executeQuery();
//			if(rs.next()) {  //여러 개가 아니라 1개 리턴되기 때문에 while이 아닌 if를 써도된다.
//				Long no = rs.getLong(1);
//				String name = rs.getString(2);
//				String email = rs.getString(3);
//				String gender = rs.getString(4);
////				Long no = rs.getLong(5);
//				
//				result = new UserVo();
//				result.setNo(no);
//				result.setName(name);
//				result.setEmail(email);
//				result.setGender(gender);
//			}
//		} catch (SQLException e) {
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
	
	
	public UserVo findByEmailAndPassword(String email, String password) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("e", email);
		map.put("p", password);
		
		return sqlSession.selectOne("user.findByEmailAndPassword",map);

	}		
		
}
