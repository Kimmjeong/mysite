package com.hanains.mysite.http.action.guestbook;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hanains.http.HttpUtil;
import com.hanains.http.action.Action;
import com.hanains.mysite.dao.GuestBookDao;
import com.hanains.mysite.vo.GuestBookVo;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		GuestBookDao dao=new GuestBookDao();
		List<GuestBookVo> list=dao.getList();
		
		request.setAttribute("list", list);
		
		HttpUtil.forwarding(request, response, "/WEB-INF/views/guestbook/list.jsp");
	}

}
