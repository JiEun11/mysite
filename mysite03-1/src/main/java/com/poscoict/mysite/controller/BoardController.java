package com.poscoict.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscoict.mysite.service.BoardService;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// 홈페이지에서 게시판 눌렀을 때의 접근 
	@RequestMapping(value="", method=RequestMethod.GET)
	public String list(@RequestParam(value="currentPage", required=true, defaultValue="1") Integer page,
			@RequestParam(value="kwd", required=true, defaultValue="")String keyword,
			@RequestParam(value="tag", required=true, defaultValue="")String tag,
			Model model) {
		
		Map<String, Object> map = boardService.getContentsList(page, keyword);
		model.addAttribute("map", map);
		
		return "board/list";
	}
	
	// 글쓰기 버튼 눌렀을 때 
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write(HttpSession session) {
		
		/* access controller */
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}
		return "board/write";
	}
	
	// 글쓰기 등록 버튼 눌렀을 때 
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(HttpSession session, BoardVo boardVo) {
		/* access controller */
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}
		boardVo.setUserNo(authUser.getNo());
		boardService.addContents(boardVo);
		return "redirect:/board";
	}
	
	@RequestMapping(value="/view/{no}", method=RequestMethod.GET)
	public String view(HttpSession session, @PathVariable("no") Long no, Model model) {
		/* access controller */
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}
		BoardVo boardVo = boardService.getContents(no);
		model.addAttribute("boardVo", boardVo);
		
		return "board/view";
	}
	
	
	@RequestMapping(value="/reply/{no}", method=RequestMethod.GET)
	public String reply(HttpSession session, @PathVariable("no") Long no, Model model) {
		
		/* access controller */
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}
		
		BoardVo boardVo = boardService.getContents(no);
//		System.out.println(boardVo.toString());
		boardVo.setOrderNo(boardVo.getOrderNo()+1);
		boardVo.setDepth(boardVo.getDepth()+1);
		
		model.addAttribute("boardVo", boardVo);
		
		return "board/reply";
	}
	
	@RequestMapping("/delete/{no}")
	public String delete(HttpSession session, @PathVariable("no") Long no ) {
		/* access controller */
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}
		boardService.deleteContents(no, authUser.getNo());
		return "redirect:/board";
	}
	
	@RequestMapping(value="/update/{no}", method=RequestMethod.GET)
	public String update(HttpSession session,  @PathVariable("no") Long no, Model model) {
		
		/* access controller */
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}
		
		BoardVo boardVo = boardService.getContents(no, authUser.getNo());
		model.addAttribute("boardVo", boardVo);
		
		return "board/update";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(HttpSession session, BoardVo boardVo) {
		
		/* access controller */
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}		

		boardService.updateContents(boardVo);
		
		return "redirect:/board";
	}
}
