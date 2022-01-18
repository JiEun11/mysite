package com.poscoict.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.mysite.dao.UserDao;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/* 회원정보수정 누르기 */
		// 접근 제어 코드 (Access Contorl 필수)
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		if (authUser == null) {
			MvcUtil.redirect(request.getContextPath() + "/user?a=loginform", request, response);
			return; // 응답이 나갔기 때문에 더 이상 접근하지 말고 끝내
		} else {
			
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			
			UserVo vo = new UserVo();
			vo.setName(name);
			vo.setNo(authUser.getNo());
			
			//session.setAttribute("authUser", vo);
			// session 객체 update 할 필요가 없다.
			vo.setGender(gender);
			
			if(password.isBlank() == false) {
				// space or empty 둘 다 처리 
				vo.setPassword(password);
			}
			
			boolean result = new UserDao().updateUser(vo);
			if(result==true) {
				authUser.setName(name);
			}
//		System.out.println(vo);
//		request.setAttribute("authUser", vo);
			MvcUtil.redirect(request.getContextPath(), request, response);
		}
	}

}
