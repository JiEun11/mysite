package com.poscoict.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping({"", "/main"})
	public String index() {
		// return "/WEB-INF/views/main/index.jsp";
		System.out.println("main cont start ");
		return "main/index";
	}
}
