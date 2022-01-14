package com.poscoict.mysite.mvc.guestbook;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.GuestbookDao;
import com.poscoict.mysite.vo.GuestbookVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class IndexAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// dao 사용해서 list 불러오고 request해서 forward 시키기 
		GuestbookDao gdao = new GuestbookDao();
		List<GuestbookVo> list = gdao.findAll();
		
		request.setAttribute("glist", list);
		MvcUtil.forward("guestbook/list", request, response);
		
	}

}
