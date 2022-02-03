package com.poscoict.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.poscoict.mysite.service.SiteService;
import com.poscoict.mysite.vo.SiteVo;

public class SiteInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private SiteService siteService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 1. ServletContext에 있는 site 값 가져오기 
		SiteVo siteVo = (SiteVo) request.getServletContext().getAttribute("site");
		
		// 2. site값이 저장 안 되어 있는 상태, 즉 처음 admin페이지에 접속했을 때라면 값 넣기 
		if(siteVo == null) {
			siteVo = siteService.getSite();
			request.getServletContext().setAttribute("siteVo", siteVo);
		}
		
		// 3. ServletContext scope에 값 넣기 완료 
		return true;
	}

	
}
