package com.poscoict.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("no");
		
//		HttpSession session = request.getSession();
//		BoardVo vo = (BoardVo)session.getAttribute("authUser");
//		BoardDao dao = new BoardDao();
		
		BoardVo vo = new BoardDao().findOne(Long.parseLong(no)); 
		// no, title, content return 
		
		request.setAttribute("vo", vo);
		
		MvcUtil.forward("/board/view", request, response);

	}

}
