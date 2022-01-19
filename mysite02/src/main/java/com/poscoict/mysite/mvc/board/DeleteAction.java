package com.poscoict.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String no = request.getParameter("no");
		
		boolean result = new BoardDao().deleteBoard(Long.parseLong(no));
		
//		if(result==true) {
//			System.out.println("삭제 성공");
//		}
		
		MvcUtil.redirect(request.getContextPath()+"/board", request, response);
	}

}
