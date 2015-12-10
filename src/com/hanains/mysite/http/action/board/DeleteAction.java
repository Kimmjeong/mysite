package com.hanains.mysite.http.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hanains.http.HttpUtil;
import com.hanains.http.action.Action;
import com.hanains.mysite.dao.BoardDao;
import com.hanains.mysite.vo.UserVo;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Long no=Long.parseLong(request.getParameter("no"));
		Long writer_no=Long.parseLong(request.getParameter("member_no"));
		
		HttpSession session=request.getSession();
		UserVo authUser=(UserVo) session.getAttribute("authUser");
		
		if(authUser==null){
			HttpUtil.redirect(response, "/mysite/board?a=list");
			return;
		}
		Long member_no=authUser.getNo();
		
		BoardDao dao=new BoardDao();

		if(writer_no==member_no)
			dao.delete(no, writer_no);

		HttpUtil.redirect(response, "/mysite/board?a=list");
		
	}

}
