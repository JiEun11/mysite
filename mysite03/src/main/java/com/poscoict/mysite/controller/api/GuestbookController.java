package com.poscoict.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.protobuf.Message;
import com.poscoict.mysite.dto.JsonResult;
import com.poscoict.mysite.service.GuestbookService;
import com.poscoict.mysite.vo.GuestbookVo;

@RestController("/guestbookApiController")
@RequestMapping("/api/guestbook")
public class GuestbookController {

	@Autowired
	private GuestbookService guestbookService;
	
	// 1. add : POST
	@PostMapping("/add")
	public Object create(@RequestBody GuestbookVo vo) {
		System.out.println("guestbook insert : " + vo );
		boolean result = guestbookService.addMessage(vo);
//		if(!result) {
//			// insert 실패 시 404 에러 뜨도록 설정한 코드 
//			return ResponseEntity.notFound().build();			
//		}
//		return JsonResult.success(vo);
		return ResponseEntity.ok().body(JsonResult.success(vo));
	}
	
	// 2. list : GET
	@GetMapping("/list/{no}")
	public Object read(@PathVariable("no") Long startNo) {
		System.out.println(startNo);
		List<GuestbookVo> list = guestbookService.getMessageList(startNo);
		System.out.println(list);
		
//		return JsonResult.success(list);
		return ResponseEntity.ok().body(JsonResult.success(list));
	}
	
	
	// 3. delete : DELETE
	@DeleteMapping("/delete/{no}")
	public Object delete(
			@PathVariable("no") Long no,
			@RequestParam(value="password", required=true, defaultValue="") String password) {
		
		System.out.println("no : " + no);
		System.out.println("password: " + password);
		
		boolean result = guestbookService.deleteMessage(no, password);

//		return JsonResult.success(no);
		return ResponseEntity.ok().body(JsonResult.success(result));
	}
}
