package com.poscoict.mysite.vo;

public class GuestbookVo {
	private int no;			// 게시판 글 번호
	private String name; 	// 사용자 이름
	private String password; // 사용자 비번 
	private String regDate; // 글 등록날짜
	private String message; // 게시판 글 
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "GuestbookVo [no=" + no + ", name=" + name + ", password=" + password + ", regDate=" + regDate
				+ ", message=" + message + "]";
	}
	
	
	
	
}
