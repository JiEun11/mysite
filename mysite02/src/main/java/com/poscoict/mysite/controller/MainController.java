package com.poscoict.mysite.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.mvc.main.MainActionFactory;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.mvc.ActionFactory;


public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
		
		String actionName = request.getParameter("a");
		
		ActionFactory af = new MainActionFactory();
		Action action = new MainActionFactory().getAction(actionName);
		action.execute(request, response);
		
		
//		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/main/index.jsp");
//		rd.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
