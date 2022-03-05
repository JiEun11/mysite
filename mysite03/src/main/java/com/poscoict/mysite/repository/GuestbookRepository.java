package com.poscoict.mysite.repository;

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
	
	public List<GuestbookVo> findAll(Long no) {
		return sqlSession.selectList("guestbook.findAllByNo", no);
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

	
}
