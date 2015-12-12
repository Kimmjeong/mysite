package com.hanains.mysite.http.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hanains.http.HttpUtil;
import com.hanains.http.action.Action;
import com.hanains.mysite.dao.BoardDao;
import com.hanains.mysite.vo.BoardVo;

public class ModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Long no=Long.parseLong(request.getParameter("no"));
		
		
		
		BoardDao dao=new BoardDao();
		BoardVo writing=dao.view(no); // 수정 양식에 보여줄 데이터
		
		
		request.setAttribute("writing", writing);
		
		HttpUtil.forwarding(request, response, "/WEB-INF/views/board/modify.jsp");

	}

}
