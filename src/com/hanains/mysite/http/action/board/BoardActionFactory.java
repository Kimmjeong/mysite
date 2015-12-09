package com.hanains.mysite.http.action.board;

import com.hanains.http.action.Action;
import com.hanains.http.action.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {

		Action action=null;
		
		if("writeform".equals(actionName)){
			action=new WriteFormAction();
		} else if("write".equals(actionName)){
			action=new WriteAction();
		} else if("view".equals(actionName)){
			action=new ViewAction();
		} else {
			action=new ListAction();
		}
		
		return action;
	}

}