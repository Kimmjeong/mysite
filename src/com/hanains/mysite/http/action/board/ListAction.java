package com.hanains.mysite.http.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hanains.http.HttpUtil;
import com.hanains.http.action.Action;
import com.hanains.mysite.dao.BoardDao;
import com.hanains.mysite.vo.UserVo;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BoardDao dao=new BoardDao();
		List<List<Object>> list=dao.getList();
		
	/*	HttpSession session=request.getSession();
		UserVo authUser=(UserVo) session.getAttribute("authUser");*/
		
		request.setAttribute("list", list);
		//request.setAttribute("authUser", authUser);
		
		HttpUtil.forwarding(request, response, "/WEB-INF/views/board/list.jsp");
	}

}
