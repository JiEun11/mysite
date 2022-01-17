package com.poscoict.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class UpdateFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/* 회원정보수정 */
		// 접근 제어 코드 (Access Contorl 필수)
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(authUser == null) {
			MvcUtil.redirect(request.getContextPath()+"/user?a=loginform", request, response);
			return;		//응답이 나갔기 때문에 더 이상 접근하지 말고 끝내
		}
		
		UserVo vo = new UserDao().findByNo(authUser.getNo());
		request.setAttribute("userVo", vo);
		MvcUtil.forward("user/updateform", request, response);
	}

}
