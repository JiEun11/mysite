<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.poscoict.mysite.vo.UserVo" %>
<%
	UserVo authUser = (UserVo)session.getAttribute("authUser");
%>
	
<div id="header">
	<h1>MySite</h1>
	<ul>
		<%
			if(authUser == null){
		%>
		<li><a href="${ pageContext.request.contextPath }/user?a=loginform">로그인</a></li>
		<li><a href="${ pageContext.request.contextPath }/user?a=joinform">회원가입</a></li>
		<%
			}else{
		%>
		<li><a href="${ pageContext.request.contextPath }/user?a=updateform">회원정보수정</a></li>
		<li><a href="${ pageContext.request.contextPath }/user?a=logout">로그아웃</a></li>
		<li>${authUser.name }님 안녕하세요 ^^;</li>
		<%
			}
		%>
	</ul>
</div>