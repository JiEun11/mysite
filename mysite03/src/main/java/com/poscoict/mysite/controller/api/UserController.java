package com.poscoict.mysite.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poscoict.mysite.dto.JsonResult;
import com.poscoict.mysite.service.UserService;
import com.poscoict.mysite.vo.UserVo;


@RestController("userApiController")
@RequestMapping("/user/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/checkemail")	//url은 소문자로 
	public Object checkEmail(
			@RequestParam(value="email", required=true, defaultValue="")String email) {
		UserVo userVo = userService.getUser(email);	//email로 user 정보 하나 가져오기 
		
		return JsonResult.success(userVo != null);
	}
}
