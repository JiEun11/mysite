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
		boolean result = false;
		
		if (authUser == null) {
			MvcUtil.redirect(request.getContextPath() + "/user?a=loginform", request, response);

			return;

		} else {
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			// groupNo, orderNo 아무 값도 없으면 ""로 들어옴 
			String groupNo = request.getParameter("groupNo");
			String orderNo = request.getParameter("orderNo");
			String depth = request.getParameter("depth");
			
			BoardVo vo = new BoardVo();
			vo.setTitle(title);		// 글 작성 시 title 
			vo.setContents(content);	// content 업뎃은 공통이므로 빼줌  
//			vo.setHit(0); db에서 default 0
			
			// 답글 작성 
			if(groupNo.isBlank() == false ) {
				vo.setGroupNo(Integer.parseInt(groupNo));
				vo.setOrderNo(Integer.parseInt(orderNo));
				
				new BoardDao().updateOrderNo(vo.getOrderNo(), vo.getGroupNo());
				vo.setHit(0);
				vo.setDepth(Integer.parseInt(depth));
				vo.setUserNo(authUser.getNo());
				vo.setUserName(authUser.getName());
			}
			// 새 글 작성 
			else {
				//vo.setGroupNo(1); dao에서 ifnull이면 1로 처리 
				//vo.setHit(0);	할 필요 없음 
				//vo.setGroupNo(0); 얘도 
				//vo.setOrderNo(0); 얘도 
				//vo.setDepth(0); 얘도 dao 에서 쿼리로 박아주면 된다. 
				vo.setUserNo(authUser.getNo());
				vo.setUserName(authUser.getName());				
			}
			
			result = new BoardDao().insert(vo);
			
			if (result == false) {
				System.out.println("게시글 작성 실패");
			} else {
				MvcUtil.redirect(request.getContextPath() + "/board", request, response);
			}
		}

	}

}
