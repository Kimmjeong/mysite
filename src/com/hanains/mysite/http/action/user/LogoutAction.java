package com.hanains.mysite.http.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hanains.http.HttpUtil;
import com.hanains.http.action.Action;

public class LogoutAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session=request.getSession();
		
		if(session == null){ // 잘못된 접근
			HttpUtil.redirect(response, "/mysite/main");
			return;
		}
		
		// 로그아웃 처리
		session.removeAttribute("authUser"); // 세션 제거
		session.invalidate(); // 새로운 세션 부여받도록함
		
		HttpUtil.redirect(response, "/mysite/main");
		
	}

}
