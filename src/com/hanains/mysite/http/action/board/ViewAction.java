package com.hanains.mysite.http.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hanains.http.HttpUtil;
import com.hanains.http.action.Action;
import com.hanains.mysite.dao.BoardDao;
import com.hanains.mysite.vo.BoardVo;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Long no=Long.parseLong(request.getParameter("no"));
		
		BoardDao dao=new BoardDao();
		
		BoardVo writing=dao.view(no);
		
		
		//조회수 업데이트
		HttpSession session = request.getSession();
		if (session.getAttribute("read") != null && session.getAttribute("read").toString().equals("no")) {
			dao.viewCount(no);
			session.setAttribute("read", "yes");
		}

		request.setAttribute("writing", writing);
		
		HttpUtil.forwarding(request, response, "/WEB-INF/views/board/view.jsp");
	}

}
