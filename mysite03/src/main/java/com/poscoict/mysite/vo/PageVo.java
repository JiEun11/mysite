package com.poscoict.mysite.vo;

public class PageVo {

	private Integer boardLimit = 5;		// 한 페이지에 보여줄 게시글 개수 
	private Integer pageLimit = 5;		// 한 페이지에 표시할 페이지 개수 
	private Integer pageTotalCnt; 		// 페이지 총 개수
	private Integer boardTotalCnt;		// 총 글 개수 
	private Integer currentPage;		// 현재 페이지
	private Integer nextPage;			// 다음 페이지 
	private Integer prePage = -1;		// 화살표 표시를 위한 변수 
	private String tag;				// 찾고자 하는 태그 저장 변수 
	private String kwd="";			// keyword 저장 변수 
	
	public int getBoardLimit() {
		return boardLimit;
	}
//	public void setBoardLimit(int boardLimit) {
//		this.boardLimit = boardLimit;
//	}

	public Integer getPageLimit() {
		return pageLimit;
	}

	public void setPageLimit(Integer pageLimit) {
		this.pageLimit = pageLimit;
	}

	public Integer getPageTotalCnt() {
		return pageTotalCnt;
	}

	public void setPageTotalCnt(Integer pageTotalCnt) {
		this.pageTotalCnt = pageTotalCnt;
	}

	public Integer getBoardTotalCnt() {
		return boardTotalCnt;
	}

	public void setBoardTotalCnt(Integer boardTotalCnt) {
		this.boardTotalCnt = boardTotalCnt;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getNextPage() {
		return nextPage;
	}

	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}

	public Integer getPrePage() {
		return prePage;
	}

	public void setPrePage(Integer prePage) {
		this.prePage = prePage;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getKwd() {
		return kwd;
	}

	public void setKwd(String kwd) {
		this.kwd = kwd;
	}
	
	
	
}
