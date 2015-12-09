package com.hanains.mysite.http.action.main;

import com.hanains.http.action.Action;
import com.hanains.http.action.ActionFactory;

public class MainActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		
		Action action=new IndexAction();
		
		return action;
	}

}
