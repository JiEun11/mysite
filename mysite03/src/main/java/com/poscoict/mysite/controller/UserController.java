package com.poscoict.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscoict.mysite.service.UserService;
import com.poscoict.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(UserVo userVo) {
		//System.out.println(userVo);
		userService.join(userVo);	//insert 작업 발생 
		return "redirect:/user/joinsuccess";	//redirect, 여기파일에 있는 경로로 리다렉 해줘야함
	}
	
	@RequestMapping(value="/joinsuccess")
	public String joinsuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(
			HttpSession session,
			@RequestParam(value="email", required=true, defaultValue="") String email,
			@RequestParam(value="password", required=true, defaultValue="")String password,
			Model model) {
		// method 이름이 login이 아니라 getUser임을 주의 
		UserVo authUser = userService.getUser(email, password); 
		System.out.println(email + ", " + password );
		
		if(authUser == null) {
			//로그인 틀렸다면 다시 로그인 해라 
			model.addAttribute("result", "fail");
			model.addAttribute("email", email);
			return "user/login";
		}
		
		/* 인증 처리 */
		session.setAttribute("authUser", authUser);	//filter로 빠져야하지만 지금은 해줌 
		
		return "redirect:/";
	}

	
	@RequestMapping(value="/logout")
	public String logout(HttpSession session) {
		
		session.removeAttribute("authUser");
		session.invalidate();
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(HttpSession session, Model model) {
		// 1. Access Controller
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {	//login도 안 하고 url로 비정상 접근한 경우 
			return "redirect:/";
		}
		
		Long userNo = authUser.getNo();
		UserVo userVo = userService.getUser(userNo);	//update쪽으로 넘겨서 값들 쓰려고
		model.addAttribute("userVo", userVo);
		return "user/update";
	}
	
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(HttpSession session, UserVo userVo) {
		// 1. Access Controller
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {	//login도 안 하고 url로 비정상 접근한 경우 
			return "redirect:/";
		}
		userVo.setNo(authUser.getNo());
		userService.updateUser(userVo);
		System.out.println(userVo);
		return "redirect:/user/update";
	}
}
