<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board" method="get">
					<input type="text" id="kwd" name="kwd" value="${keyword }">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>				
					<!--<c:set var="count" value="${fn:length(list) }" />-->
					<!--<c:set var="count" value="${map.pageVo.boardTotalCnt }"/> -->
					<c:forEach items="${map.list }" var="vo" varStatus="status">
					<tr>
						<td>${count- status.index- (map.pageVo.currentPage -1)* map.pageVo.boardLimit }</td>
						<!-- <td>${status.count }</td>-->
						<c:choose>
							<c:when test="${0 eq vo.depth}">
								<td style="text-align:left; padding-left:0px">
								<a href="${pageContext.request.contextPath }/board/view/${vo.no}">${vo.title }</a></td>
							</c:when>
							<c:otherwise>
								<td style="text-align:left; padding-left:${20*vo.depth }px">
								<img src="${pageContext.request.contextPath }/assets/images/reply.png" />
								<a href="${pageContext.request.contextPath }/board/view/${vo.no}">${vo.title }</a></td>
							</c:otherwise>
						</c:choose>
						<td>${vo.userName }</td>
						<td>${vo.hit }</td>
						<td>${vo.regDate }</td>
						<c:if test="${not empty authUser && authUser.no eq vo.userNo }">
							<td><a href="${pageContext.request.contextPath }/board/delete/${vo.no}" class="del" style='background-image: url("${pageContext.servletContext.contextPath }/assets/images/recycle.png")'>삭제</a></td>
						</c:if>
						
					</tr>
					
				</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
					<c:if test="${map.pageVo.currentPage ne 1 }">
						<li><a href="">◀</a></li>
					</c:if>
						<c:forEach var="var" begin="1" end="${map.pageVo.pageTotalCnt }" step="1" varStatus="status">
							<c:choose>
								<c:when test="${var == map.pageVo.currentPage }">
									<li class="selected">
								</c:when>
								<c:otherwise>
									<li>
								</c:otherwise>
							</c:choose>
						 		<a href="${pageContext.request.contextPath }/board?currentPage=${status.count }&kwd=${map.pageVo.kwd }&tag=${map.pageVo.tag }">${status.count }</a>
							</li>
						</c:forEach>
						
						<!--  <li><a href="">1</a></li>
						<li class="selected">2</li>
						<li><a href="">3</a></li>
						<li>4</li>
						<li>5</li>
						-->
						<c:if test="${map.pageVo.currentPage ne map.pageVo.pageTotalCnt }">
						<li><a href="">▶</a></li>
						</c:if>
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<c:choose>
					<c:when test='${empty authUser }'>
						<div class="bottom">
							<a href="${pageContext.request.contextPath }/user/login" id="new-book">로그인을 해주세요</a>
						</div>
					</c:when>
					
					<c:otherwise>
						<div class="bottom">
							<a href="${pageContext.request.contextPath }/board/write" id="new-book">글쓰기</a>
						</div>				
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>