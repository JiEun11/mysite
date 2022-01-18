package com.poscoict.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;

/**
 * Servlet Filter implementation class EncodingFilter
 */
public class EncodingFilter implements Filter {
	private String encoding;
	
	public void init(FilterConfig fConfig) throws ServletException {
		encoding = fConfig.getInitParameter("encoding");
		
		//default encoding 필요 if 설정에서 빼먹었다면 
		if(encoding == null) {
			encoding = "utf-8";
		}
	}
         
	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// request 처리 
		request.setCharacterEncoding(encoding);
		
		// pass the request along the filter chain
		chain.doFilter(request, response); // 여기서 servlet이 호출된다.
		
		// response 처리 
	}

}
