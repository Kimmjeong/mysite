package com.hanains.mysite.http.action.board;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hanains.http.HttpUtil;
import com.hanains.http.action.Action;
import com.hanains.mysite.dao.BoardDao;
import com.hanains.mysite.vo.BoardVo;
import com.hanains.mysite.vo.UserVo;

public class ModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Long no=Long.parseLong(request.getParameter("no"));
		
		HttpSession session=request.getSession();
		UserVo authUser=(UserVo) session.getAttribute("authUser");
		
		BoardDao dao=new BoardDao();
		BoardVo writing=dao.view(no); // 게시글 정보
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		// 작성자 확인
		if(authUser==null || authUser.getNo()!=writing.getMember_no()){
			out.print("<script>alert('작성자가 아닙니다.');history.back()</script>");
			out.flush();
			out.close();
			return;
		}
		
		request.setAttribute("writing", writing);
		
		HttpUtil.forwarding(request, response, "/WEB-INF/views/board/modify.jsp");

	}

}
