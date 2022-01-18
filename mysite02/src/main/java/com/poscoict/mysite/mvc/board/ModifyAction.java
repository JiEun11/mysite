package com.poscoict.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("no");
		String title = request.getParameter("title");
//		System.out.println(name);
		String content = request.getParameter("content");
//		System.out.println(content);
		
		BoardVo vo = new BoardVo();
		vo.setNo(Long.parseLong(no));
		vo.setTitle(title);
		vo.setContents(content);
		
		boolean result = new BoardDao().updateBoard(vo);
//		if(result==true) {
//			System.out.println("update 성공");
//		}
		
		MvcUtil.redirect(request.getContextPath()+"/board", request, response);
	}

}
