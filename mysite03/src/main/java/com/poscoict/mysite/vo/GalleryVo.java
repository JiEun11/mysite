package com.poscoict.mysite.vo;

public class GalleryVo {
	Long no;		// gallery 번호 
	String url;		// gallery image 저장 url 
	String comments;	// gallery image comments
	
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	@Override
	public String toString() {
		return "GalleryVo [no=" + no + ", url=" + url + ", comments=" + comments + "]";
	}
	
	
}
