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
		// 1. handler 종류 확인 필요
		if (handler instanceof HandlerMethod == false) {
			return true; // true면 Controller에 있는 메서드가 아니므로 그대로 Controller 진행
		}

		// 2. casting
		HandlerMethod handlerMethod = (HandlerMethod) handler;

		// 3. Handler Method의 @Auth 받아오기
		// 없으면 auth에 null을 return한다.
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);

		// 4. Handler Method @Auth가 없으면 Type(Class)에 있는지 확인(과제)
		if (auth == null) {
//			auth = handlerMethod.getBeanType().getAnnotation(Auth.class);	내가 한 코드 
			auth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);
		}

		// 5. type(4)과 method(3)에 @Auth가 적용이 안 되어 있는 경우
		if (auth == null) {
			return true; // @Auth가 없다면 인증 필요없다는 소리니까 return true로 cont 실행
		}

		// 6. @Auth가 적용되어 있기 때문에 인증(Authentication) 여부 확인
		HttpSession session = request.getSession();
		if (session == null) { // session이 있는지 체크
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false; // 인증해야하니까 cont로 돌리지 않게끔 false
		}

		// 7. session이 존재하면 유효한 유저인지 확인
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false; // 인증해야하니까 cont로 돌리지 않게끔 false;
		}

		// 8. 권한(Authorization)체크를 위해서 @Auth의 role 가져오기 ("USER","ADMIN")
		// 위에서 auth == null , authUser == null인 경우 다 튕겨냈으니까 여기까지 왔을 때에는 NULL이 절대 아님
		String role = auth.role();
//		if("ADMIN".equals(role)) {
//			// admin이 아니면 메인 페이지로 돌리기 
//			if("ADMIN".equals(authUser.getRole()) == false) {
//				response.sendRedirect(request.getContextPath());
//				return false;
//			}
//		}
		// 9. @Auth의 role이 "USER" 인 경우 authUser의 role은 상관이 없음
		if ("USER".equals(role)) {
			return true;
		}
		
		// 10. @Auth의 role이 "ADMIN"인 경우, authUser의 role은 ADMIN 이여야 한다.
		if("ADMIN".equals(authUser.getRole()) == false) {
			response.sendRedirect(request.getContextPath());
			return false;
		}

		// 11. 옳은 관리자 : 접근 허가, 인증 확인 완료 !! return true로 cont의 handler(method) 실행
		// @Auth role : "ADMIN"
		// authUser의 role : "ADMIN" 인 경우 
		return true;	// AdminController가 실행된다. 
	}

}
