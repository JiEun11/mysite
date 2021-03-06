package com.poscoict.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscoict.mysite.security.Auth;
import com.poscoict.mysite.security.AuthUser;
import com.poscoict.mysite.service.BoardService;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.util.WebUtil;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// 홈페이지에서 게시판 눌렀을 때의 접근 
	@RequestMapping(value="", method=RequestMethod.GET)
	public String list(@RequestParam(value="p", required=true, defaultValue="1") Integer page,
			@RequestParam(value="kwd", required=true, defaultValue="")String keyword,
			Model model) {
		Map<String, Object> map = boardService.getContentsList(page, keyword);
		model.addAttribute("map", map);
		
		return "board/list";
	}
	
	// 글쓰기 버튼 눌렀을 때 
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write() {
		
//		/* access controller */ auth annotation을 사용함으로써 없앨 수 있음 
//		UserVo authUser = (UserVo)session.getAttribute("authUser");
//		if(authUser == null) {
//			return "redirect:/board";
//		}
		return "board/write";
	}
	
	// 글쓰기 등록 버튼 눌렀을 때 
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(@AuthUser UserVo authUser, BoardVo boardVo,
			@RequestParam(value="p", required=true, defaultValue="1") Integer page,
			@RequestParam(value="kwd", required=true, defaultValue="") String keyword) {
//		/* access controller */
//		UserVo authUser = (UserVo)session.getAttribute("authUser");
//		if(authUser == null) {
//			return "redirect:/board";
//		}
		boardVo.setUserNo(authUser.getNo());
		boardService.addContents(boardVo);
		return "redirect:/board?p=" + page + "&kwd=" + WebUtil.encodeURL(keyword, "UTF-8");
	}
	
	@Auth
	@RequestMapping(value="/view/{no}", method=RequestMethod.GET)
	public String view(@PathVariable("no") Long no, Model model) {

//		/* access controller */
//		UserVo authUser = (UserVo)session.getAttribute("authUser");
//		if(authUser == null) {
//			return "redirect:/board";
//		}
		BoardVo boardVo = boardService.getContents(no);
		model.addAttribute("boardVo", boardVo);
		
		return "board/view";
	}
	
	
	@RequestMapping(value="/reply/{no}", method=RequestMethod.GET)
	public String reply(@AuthUser UserVo authUser, @PathVariable("no") Long no, Model model) {
		
//		/* access controller */
//		UserVo authUser = (UserVo)session.getAttribute("authUser");
//		if(authUser == null) {
//			return "redirect:/board";
//		}
		
		BoardVo boardVo = boardService.getContents(no);
//		System.out.println(boardVo.toString());
		boardVo.setOrderNo(boardVo.getOrderNo()+1);
		boardVo.setDepth(boardVo.getDepth()+1);
		
		model.addAttribute("boardVo", boardVo);
		
		return "board/reply";
	}
	
	@RequestMapping("/delete/{no}")
	public String delete(@AuthUser UserVo authUser, @PathVariable("no") Long no,
					@RequestParam(value="p", required=true, defaultValue="1") Integer page,
					@RequestParam(value="kwd", required=true, defaultValue="") String keyword) {
//		/* access controller */
//		UserVo authUser = (UserVo)session.getAttribute("authUser");
//		if(authUser == null) {
//			return "redirect:/board";
//		}
		boardService.deleteContents(no, authUser.getNo());
		return "redirect:/board?p=" + page + "&kwd=" + WebUtil.encodeURL(keyword, "UTF-8");
	}
	
	@RequestMapping(value="/update/{no}", method=RequestMethod.GET)
	public String update(@AuthUser UserVo authUser,  @PathVariable("no") Long no, Model model) {
		
//		/* access controller */
//		UserVo authUser = (UserVo)session.getAttribute("authUser");
//		if(authUser == null) {
//			return "redirect:/board";
//		}
		
		BoardVo boardVo = boardService.getContents(no, authUser.getNo());
		model.addAttribute("boardVo", boardVo);
		
		return "board/update";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(@AuthUser UserVo authUser, BoardVo boardVo,
			@RequestParam(value="p", required=true, defaultValue="1") Integer page,
			@RequestParam(value="kwd", required=true, defaultValue="") String keyword) {
		
//		/* access controller */
//		UserVo authUser = (UserVo)session.getAttribute("authUser");
//		if(authUser == null) {
//			return "redirect:/board";
//		}		
		
		boardVo.setUserNo(authUser.getNo());
		boardService.updateContents(boardVo);
		
		return "redirect:/board/view/" + boardVo.getNo() + "?p=" + page + "&kwd=" + WebUtil.encodeURL(keyword, "UTF-8");
	}
}
