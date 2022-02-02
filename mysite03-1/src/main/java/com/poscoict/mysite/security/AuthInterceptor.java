package com.poscoict.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.poscoict.mysite.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 1. Handler 종류 확인 필요 
		if( handler instanceof HandlerMethod == false){
			return true;
		}
		
		// 2. casting
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		System.out.println("handlerMethod : " + handlerMethod);
		
		// 3. Handler Method의 @Auth 받아오기 
		// 없으면 auth에 null return
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		System.out.println("auth : " + auth);
		
		// 4. @Auth가 없다면 인증 필요 없다는 소리니까 return true로 cont 실행 
		if(auth==null) {
			return true;
		}
		
		// 5. @Auth가 적용되어 있기 때문에 인증(Authentication) 여부 확인
		// type과 method에 @Auth가 적용이 안 되어 있는 경우 
		HttpSession session = request.getSession();
		if(session==null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;	// 인증 필요하니까 Cont로 가지 않게끔 false 처리 
		}
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;	// 인증 필요하니까 Cont로 가지 않게끔 false 처리 
		}
		
		// 4, 5까지 왔다는 건 인증이 확인 되었다는 소리이므로 
		// 6. 인증 확인 완료 되었으니까 return true로 Cont의 Handler(method) 실행 
		return true;
	}

	
}
