package com.poscoict.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.poscoict.mysite.vo.UserVo;

public class AuthUserHandlermethodArgumentResolver extends HandlerMethodArgumentResolverComposite {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		
		System.out.println("authUser 받기 전!!!!!");
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		
		System.out.println("authUser 받은 후 !!!!!");
		
		// AuthUser가 안 붙어있으면 
		if(authUser == null) {
			return false;
		}
		
		// parameter type이 UserVo type인지 확인 
		if(parameter.getParameterType().equals(UserVo.class) == false) {
			return false;
		}
		return true;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		
		if(!supportsParameter(parameter)) {
			return WebArgumentResolver.UNRESOLVED;
		}
		System.out.println("WAS에게 요청 전!!! ");
		
		//WAS에게 현재 request 객체를 return 해달라고 요청 
		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		
		HttpSession session  = request.getSession();
		if(session == null) {
			return null;
		}
		
		return session.getAttribute("authUser");
	}
	
	
}
