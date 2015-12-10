package com.hanains.mysite.http.action.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hanains.http.HttpUtil;
import com.hanains.http.action.Action;
import com.hanains.mysite.dao.GuestBookDao;

public class DeleteFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Long no=Long.parseLong(request.getParameter("no"));
		String password=request.getParameter("password");
		
		request.setAttribute("no", no);
		request.setAttribute("password", password);
		
		// 비밀번호 일치 확인 용도
		GuestBookDao dao=new GuestBookDao();
		request.setAttribute("ispwd", dao.isPassword(no));
		
		HttpUtil.forwarding(request, response, "/WEB-INF/views/guestbook/deleteform.jsp");
	}

}
