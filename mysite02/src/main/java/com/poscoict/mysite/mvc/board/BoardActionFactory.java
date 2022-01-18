package com.poscoict.mysite.mvc.board;

import com.poscoict.web.mvc.Action;
import com.poscoict.web.mvc.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if("writeform".equals(actionName)) {
			action = new WriteFormAction();
		}
		else if("write".equals(actionName)) {
			action = new WriteAction();
		}
		else if("view".equals(actionName)) {
			action = new viewAction();
		}
		else {		// 아무것도 안 해줬을 때 list 보여주기 
			action = new ListAction();
		}
		return action;
	}

}
