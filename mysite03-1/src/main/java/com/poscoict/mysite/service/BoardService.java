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
		
		return false;
	}
	
	public BoardVo getContents(Long no) {
		
		return null;
	}
	
	public BoardVo getContents(Long no, Long userNo) {
		
		return null;
	}
	
	public boolean updateContents(BoardVo boardVo) {
		
		return false;
	}
	
	public boolean deleteContents(Long boardNo, Long userNo) {
		
		return false;
	}
	
	public Map<String, Object> getContentsList(int currentPage, String keyword){
		
		return null;
	}

}
