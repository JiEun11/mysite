package com.poscoict.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.poscoict.mysite.service.UserService;
import com.poscoict.mysite.vo.UserVo;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserVo authUser = userService.getUser(email, password);
		System.out.println(email + ", " + password );
		
		if(authUser == null) { //로그인 틀렸다면 다시 로그인 해라 
			request.setAttribute("result","fail");
			request.setAttribute("email", email);
			request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, response);
			
			return false;	// controller로 못 감 
		}
			
		HttpSession session = request.getSession(true);	//없으면 생성 
		session.setAttribute("authUser", authUser);		//filter로 빠져야하지만 지금은 해줌
		response.sendRedirect(request.getContextPath());
		
		return false;
	}
	
}
