package com.poscoict.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.mysite.repository.BoardRepository;
import com.poscoict.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	private static final int LIST_SIZE = 5;  // 리스팅 되는 게시물의 수 
	private static final int PAGE_SIZE = 5;  // 페이지 리스트의 페이지 수 
	
	@Autowired
	private BoardRepository boardRepository;
	
	public boolean addContents(BoardVo boardVo) {
		if(boardVo.getGroupNo() != null) {
			boardRepository.updateOrderNo(boardVo.getGroupNo(), boardVo.getOrderNo());
		}
		return boardRepository.insert(boardVo)==1;
	}
	
	public BoardVo getContents(Long no) {
		BoardVo boardVo = boardRepository.findByNo(no);
		
		if(boardVo != null) {
			boardRepository.updateHit(no);
		}
		return boardVo;
	}
	
	public BoardVo getContents(Long no, Long userNo) {
		BoardVo boardVo = boardRepository.findByNoAndUserNo(no, userNo);
		return boardVo;
	}
	
	public boolean updateContents(BoardVo boardVo) {
		int count = boardRepository.update(boardVo);
		return count == 1;
	}
	
	public boolean deleteContents(Long boardNo, Long userNo) {
		int count = boardRepository.delete(boardNo, userNo);
		return count == 1;
	}
	
	public Map<String, Object> getContentsList(int currentPage, String keyword){
		
		//1. 페이징을 위한 기본 데이터 계산
		int totalCount = boardRepository.getTotalCount(keyword);
		int pageCount = (int)Math.ceil((double)totalCount / LIST_SIZE);
		int blockCount = (int)Math.ceil((double)pageCount / PAGE_SIZE);
		int currentBlock = (int)Math.ceil((double)currentPage / PAGE_SIZE);
		return null;
	}

}
