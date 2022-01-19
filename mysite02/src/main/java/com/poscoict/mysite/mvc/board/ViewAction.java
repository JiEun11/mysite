package com.poscoict.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String boardNo = request.getParameter("no");
		Long no = Long.parseLong(boardNo);
		
		BoardVo vo = new BoardDao().findOne(no); 
		// no, title, content, groupNo, orderNo, depth, hit return 
		
		request.setAttribute("vo", vo);
		
		// 쿠키 읽기 
		Cookie[] cookies = request.getCookies();
		Cookie viewCookie = null;
		
		if(cookies != null && cookies.length > 0 ) {
			for(Cookie cookie : cookies) {
				if(boardNo.equals(cookie.getName())) {
					viewCookie = cookie;	// cookie를 저장 
					break;	//다른 쿠키에는 관심이 없으므로
				}
			}
		}
		
		
		// 쿠키 굽기 
		if(viewCookie == null) {
			Cookie cookie = new Cookie(boardNo, String.valueOf(vo.getHit() +1));
			cookie.setPath(request.getContextPath());
			cookie.setMaxAge(60);
			response.addCookie(cookie);	//응답으로 set cookie 해서 나감 
			new BoardDao().hitUp(vo);
		}
		
		MvcUtil.forward("/board/view", request, response);

	}

}
