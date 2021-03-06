package com.poscoict.mysite.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.PageVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BoardDao dao = new BoardDao();
		PageVo pvo = new PageVo();
		
		String tag = request.getParameter("search_tag");
//		System.out.println("tag: "+tag);
		String kwd = request.getParameter("kwd");	//처음엔 Null, 다음엔 공백
//		System.out.println("kwd: "+kwd);
		String currentPage = request.getParameter("currentPage");
//		int currentPage = 0;
		
		int boardTotalCnt = dao.boardTotalCnt(tag, kwd);	// 게시글 총 개수
//		System.out.println("게시글 총 개수 " +boardTotalCnt);
		
		if(tag != null) {	// tag 값 셋팅 
			pvo.setTag(tag);
		}
		if(kwd != null && kwd.isBlank()==false) {
			pvo.setKwd(kwd);	// kwd 공백이나 null 아니면 객체에 셋팅 
		}
		
		int pageTotalCnt = boardTotalCnt / pvo.getBoardLimit();	// 페이지 총 개수
		if( boardTotalCnt % pvo.getBoardLimit() >= 1) {
			pageTotalCnt ++;
		}
//		System.out.println("pageTotalCnt " + pageTotalCnt);
		
		if(currentPage != null) {
			pvo.setCurrentPage(Integer.parseInt(currentPage));
		}
		else {
			pvo.setCurrentPage(1);
		}
		
		pvo.setBoardTotalCnt(boardTotalCnt);
		pvo.setPageTotalCnt(pageTotalCnt);
		
		request.setAttribute("pvo", pvo);
		
//		List<BoardVo> list = dao.findAll();
		List<BoardVo> list = dao.findAll(pvo.getCurrentPage(), pvo.getBoardLimit(), tag, kwd);
		request.setAttribute("list", list);
		MvcUtil.forward("board/list", request, response);

	}

}
