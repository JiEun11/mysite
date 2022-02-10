package com.poscoict.mysite.repository;

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
	}
	
	/*
	 * 글 수정 눌렀을 때  
	 */
	public boolean updateBoard(BoardVo vo) {
		int count = sqlSession.update("board.updateBoard",vo);
		return count==1;

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
	}
	
	/*
	 * 조회수 증가 dao 
	 */
	public boolean hitUp(BoardVo vo) {
		int count = sqlSession.update("board.hitUp", vo);
		return count == 1;

	}
	
}
