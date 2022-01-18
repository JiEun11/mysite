package com.poscoict.mysite.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		int pageCount = 10;
//		int currentPage = 2;
//		int nextPage = -1;
//		int startPage = 3;
//		int prePage 는 2가 되겠다. link는 2를 줘야겠다
//		pagerInfo 객체를 만들던지
//		Map m;
//		m.put으로 담아놓고 request 통해서넘겨놓고..
		BoardDao dao = new BoardDao();
		List<BoardVo> list = dao.findAll();
		
		request.setAttribute("list", list);
		MvcUtil.forward("board/list", request, response);

	}

}
