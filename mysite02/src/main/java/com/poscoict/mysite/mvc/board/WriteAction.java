package com.poscoict.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// post 방식에서는 한글 처리 필수이지만 encoding filter를 만들어줬으므로 필요없음
		// request.setCharacterEncoding("utf-8");

		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		if (authUser == null) {
			MvcUtil.redirect(request.getContextPath() + "/user?a=loginform", request, response);

			return;

		} else {
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			BoardVo vo = new BoardVo();
			vo.setTitle(title);
			vo.setContents(content);
			vo.setUserNo(authUser.getNo());
			vo.setUserName(authUser.getName());
			boolean result = new BoardDao().insert(vo);
			
			if (result == false) {
				System.out.println("게시글 작성 실패");
			} else {
				MvcUtil.redirect(request.getContextPath() + "/board", request, response);
			}
		}

	}

}
