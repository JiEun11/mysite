package com.poscoict.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class LogoutAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/* logout 처리 */
		HttpSession session = request.getSession();
		session.removeAttribute("authUser");
		session.invalidate();	// authUser 객체 삭제
		
		
		MvcUtil.redirect(request.getContextPath(), request, response);	// main으로 돌리자
	}

}
