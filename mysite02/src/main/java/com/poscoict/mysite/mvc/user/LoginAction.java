package com.poscoict.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.mysite.dao.UserDao;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class LoginAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		UserVo authUser = new UserDao().findByEmailAndPassword(email, password); // return null 이면 login failed
		
		if(authUser == null) {
			/* 이메일 또는 비밀번호가 틀림 */
			request.setAttribute("result", "fail");  
			request.setAttribute("email", email);
			MvcUtil.forward("user/loginform", request, response);
			return;
		}
		
		// 인증 성공 --> session 처리
		HttpSession session = request.getSession(true);		//있으면 달라, 없으면 새로 만들어서 달라
		session.setAttribute("authUser", authUser);
		
		MvcUtil.redirect(request.getContextPath(), request, response); // main으로 돌리기
		
	}

}
