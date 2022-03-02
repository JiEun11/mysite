package com.poscoict.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poscoict.mysite.dto.JsonResult;
import com.poscoict.mysite.service.GuestbookService;
import com.poscoict.mysite.vo.GuestbookVo;

@RestController("/guestbookApiController")
@RequestMapping("/api/guestbook")
public class GuestbookController {

	@Autowired
	private GuestbookService guestbookService;
	
	// 1. add : POST

	// 2. list : GET
	@GetMapping("")
	public Object read(@RequestParam(value="no", required=true, defaultValue="-1") Long no) {
		
		List<GuestbookVo> list = guestbookService.getMessageList();
		
		System.out.println(list);
		
 		
		return JsonResult.success(list);
	}
	
	
	// 3. delete : DELETE

}
