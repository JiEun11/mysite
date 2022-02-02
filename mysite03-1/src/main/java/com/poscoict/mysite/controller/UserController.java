package com.poscoict.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscoict.mysite.security.Auth;
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
	
	@Auth
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
