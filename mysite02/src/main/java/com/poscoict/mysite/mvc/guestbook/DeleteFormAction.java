package com.poscoict.mysite.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.GuestbookDao;
import com.poscoict.mysite.vo.GuestbookVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class DeleteFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String no = request.getParameter("no");
		String password = request.getParameter("password");
		
		GuestbookVo gbvo = new GuestbookVo();
		gbvo.setNo(Integer.parseInt(no));
		gbvo.setPassword(password);
		
		new GuestbookDao().delete(gbvo.getNo(), gbvo.getPassword());
		
		MvcUtil.redirect("/mysite02/guestbook", request, response);
		
	}

}
