package com.hanains.mysite.http.action.guestbook;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hanains.http.HttpUtil;
import com.hanains.http.action.Action;
import com.hanains.mysite.dao.GuestBookDao;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		
		Long no=Long.parseLong(request.getParameter("no"));
		String password=request.getParameter("password");
		
		GuestBookDao dao=new GuestBookDao();

		// 비밀번호 일치 확인
		if(!(dao.isPassword(no,password))){
			HttpUtil.redirect(response, "/mysite/guestbook?a=deleteform&no="+no);
			return;
		}
		
		dao.delete(no, password);

		HttpUtil.redirect(response, "/mysite/guestbook");

	}

}
