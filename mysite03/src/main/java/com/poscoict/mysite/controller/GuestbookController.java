package com.poscoict.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscoict.mysite.service.GuestbookService;
import com.poscoict.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {

	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping(value="/spa")
	public String spa() {
		return "guestbook/index-spa";
	}
	
	// 홈페이지에서 방명록 눌렀을 때의 접근 
	@RequestMapping(value="", method=RequestMethod.GET)
	public String guestbook(Model model) {
		List<GuestbookVo> list = guestbookService.getMessageList();
		model.addAttribute("list", list);
		return "guestbook/list";
	}
	
	// 확인버튼 눌렀을 때 (글 삽입)
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String guestbook(GuestbookVo vo) {
		System.out.println("guestbookvo insert 전 : " + vo);
		guestbookService.addMessage(vo);	// insert 작업 발생 
//		System.out.println(vo.toString());
		System.out.println("guestbookvo insert 후 : " +vo);
		return "redirect:/guestbook";		// 새로고침 시 insert 계속 안 되도록 redirect
	}
	
	// 삭제 버튼 눌렀을 때 
	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String deleteform(@PathVariable("no") Long no, Model model) {
//		System.out.println(no);
		model.addAttribute("no",no);
		return "guestbook/deleteform";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(Long no, String password) {
		guestbookService.deleteMessage(no, password);
		
		return "redirect:/guestbook";			
	}
}
