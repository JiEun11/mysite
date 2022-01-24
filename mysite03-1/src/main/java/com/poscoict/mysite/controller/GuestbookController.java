package com.poscoict.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscoict.mysite.service.GuestbookService;
import com.poscoict.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {

	@Autowired
	private GuestbookService guestbookService;
	
	// 홈페이지에서 방명록 눌렀을 때의 접근 
	@RequestMapping(value="")
	public String list(Model model) {
		List<GuestbookVo> list = guestbookService.getMessageList();
		model.addAttribute("list", list);
		return "guestbook/list";
	}
	
	// 확인버튼 눌렀을 때 (글 삽입)
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(GuestbookVo vo) {
		guestbookService.addMessage(vo);	// insert 작업 발생 
//		System.out.println(vo.toString());
		return "redirect:/guestbook";		// 새로고침 시 insert 계속 안 되도록 redirect
	}
	
	// 삭제 버튼 눌렀을 때 
	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String deleteform(@PathVariable("no") Long no, Model model) {
//		System.out.println(no);
		model.addAttribute("no",no);
		return "guestbook/delete";
	}
	
	@RequestMapping(value="/delete/{no}", method=RequestMethod.POST)
	public String delete(@PathVariable("no") Long no, @RequestParam(value="password", required=true, defaultValue="") String password) {
		guestbookService.deleteMessage(no, password);
		
		return "redirect:/guestbook";			
	}
}
