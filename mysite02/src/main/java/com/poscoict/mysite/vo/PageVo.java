package com.poscoict.mysite.vo;

public class PageVo {

	private int boardLimit = 7;		// 한 페이지에 보여줄 게시글 개수 
	private int pageTotalCnt; 	// 페이지 총 개수
	private int boardTotalCnt;		// 총 글 개수 
	private int currentPage;	// 현재 페이지
	private int nextPage;		// 다음 페이지 
	private int prePage = -1;		// 화살표 표시를 위한 변수 
	
	public int getBoardLimit() {
		return boardLimit;
	}
//	public void setBoardLimit(int boardLimit) {
//		this.boardLimit = boardLimit;
//	}
	public int getPageTotalCnt() {
		return pageTotalCnt;
	}
	public void setPageTotalCnt(int pageTotalCnt) {
		this.pageTotalCnt = pageTotalCnt;
	}
	public int getBoardTotalCnt() {
		return boardTotalCnt;
	}
	public void setBoardTotalCnt(int boardTotalCnt) {
		this.boardTotalCnt = boardTotalCnt;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public int getPrePage() {
		return prePage;
	}
	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}
	
	
	
}
