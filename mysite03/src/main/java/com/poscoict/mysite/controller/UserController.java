package com.poscoict.mysite.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public String join(@ModelAttribute UserVo vo) {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo vo, BindingResult result, Model model) {
		//System.out.println(userVo);
		
		if(result.hasErrors()) {
			// 여기에 들어왔다는 건 result 체크가 유효하지 않다는 뜻
//			List<ObjectError> list = result.getAllErrors();		//error있는 애들 다 끄집어 내기 
//			for(ObjectError error :list) {
//				System.out.println(error);
//			}
//			model.addAttribute("userVo", userVo);		// name을 쓸 수 있게 값을 넘김
			model.addAllAttributes(result.getModel());	// result 결과를 가지고다시 join으로 감
			return "user/join";	//forwarding 시킴 
		}
		
		userService.join(vo);	//insert 작업 발생 
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
	
//	@RequestMapping(value="/login", method=RequestMethod.POST)
//	public String login(
//			HttpSession session,
//			@RequestParam(value="email", required=true, defaultValue="") String email,
//			@RequestParam(value="password", required=true, defaultValue="")String password,
//			Model model) {
//		// method 이름이 login이 아니라 getUser임을 주의 
//		UserVo authUser = userService.getUser(email, password); 
//		System.out.println(email + ", " + password );
//		
//		if(authUser == null) {
//			//로그인 틀렸다면 다시 로그인 해라 
//			model.addAttribute("result", "fail");
//			model.addAttribute("email", email);
//			return "user/login";
//		}
//		
//		/* 인증 처리 */
//		session.setAttribute("authUser", authUser);	//filter로 빠져야하지만 지금은 해줌 
//		
//		return "redirect:/";
//	}

	
//	@RequestMapping(value="/logout")
//	public String logout(HttpSession session) {
//		
//		session.removeAttribute("authUser");
//		session.invalidate();
//		
//		return "redirect:/";
//	}
	
//	@Auth(values="USER")
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
	
//	@ExceptionHandler(Exception.class)
//	public String UserControllerExceptionHandler() {
//		return "error/exception";
//	}
}
