package com.poscoict.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.mysite.repository.BoardRepository;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.PageVo;

@Service
public class BoardService {
	
	private static final int LIST_SIZE = 5;  // 리스팅 되는 게시물의 수 
	private static final int PAGE_SIZE = 5;  // 페이지 리스트의 페이지 수 
	
	@Autowired
	private BoardRepository boardRepository;
	
	// 새 글, 답글 
	public Boolean addContents(BoardVo vo) {
		if(vo.getGroupNo() != null) {
			System.out.println(vo);
			boardRepository.updateOrderNo(vo.getOrderNo(), vo.getGroupNo());
			System.out.println(vo);
//			vo.setOrderNo(vo.getOrderNo()+1);
//			vo.setDepth(vo.getDepth()+1);
		}
		return boardRepository.insert(vo);
	}
	

	// 글보기 view
	public BoardVo getContents(Long no) {
		BoardVo boardVo = boardRepository.findOne(no);
		System.out.println(boardVo);
		if(boardVo != null )
			boardRepository.hitUp(boardVo);
		
		return boardVo;
	}
	
	// 글 수정하기 전 
	public BoardVo getContents(Long no, Long userNo) {
		BoardVo boardVo = boardRepository.findByNoAndUserNo(no, userNo);
		return boardVo;
	}
	
	
	// 글 수정 
	public Boolean updateContents(BoardVo vo) {
		
		return boardRepository.updateBoard(vo);
	}
	
	// 글 삭제 
	public Boolean deleteContents(Long no, Long userNo) {
		
		return boardRepository.deleteBoard(no, userNo);
	}
	
	// 글 리스트(찾기 결과)
	public Map<String, Object> getContentsList(int currentPage, String keyword, String tag) {
		PageVo pageVo = new PageVo();
		pageVo.setCurrentPage(currentPage);
		pageVo.setKwd(keyword);
		pageVo.setTag(tag);
		
		// 1. pagination을 위한 데이터 계산]
		int boardTotalCount = boardRepository.boardTotalCnt(pageVo.getTag(), pageVo.getKwd());
		int pageCount = (int)Math.ceil((double)boardTotalCount / pageVo.getPageLimit());
		int blockCount = (int)Math.ceil((double)pageCount / pageVo.getBoardLimit());
//		int currentBlock = (int)Math.ceil((double)currentPage / PAGE_SIZE);
		
		// 2. page 값 조건 설정 
//		if(currentPage > pageCount) {
//			currentPage = pageCount;
//			currentBlock = (int)Math.ceil((double)currentPage / PAGE_SIZE);
//		}
//		
//		if(currentPage < 1 ) {
//			currentPage = 1;
//			currentBlock = 1;
//		}
		
		// 3. view에서 page list를 렌더링 하기 위한 데이터 값 계산
		
		// 3. PageVo 객체에 값 세팅 
		pageVo.setBoardTotalCnt(boardTotalCount);
		pageVo.setPageTotalCnt(pageCount);
		
		// 4. 리스트 가져오기 
		List<BoardVo> list = boardRepository.findAll(pageVo.getCurrentPage(), pageVo.getBoardLimit(), pageVo.getTag(), pageVo.getKwd());
//		List<BoardVo> list = boardRepository.findAll(pageVo);

//		for(BoardVo vo : list) {
//			System.out.println(vo.toString());
//		}
		// 5. 리스트 정보를 맵에 저장 
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("list",list);
		map.put("pageVo", pageVo);
		return map;
	}
	

}
