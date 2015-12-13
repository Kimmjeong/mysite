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
import com.hanains.mysite.vo.UserVo;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Long no=Long.parseLong(request.getParameter("no"));
		Long writer_no=Long.parseLong(request.getParameter("member_no"));
		
		HttpSession session=request.getSession();
		UserVo authUser=(UserVo) session.getAttribute("authUser");
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		// 작성자 확인
		if(authUser==null || authUser.getNo()!=writer_no){
			out.print("<script>alert('작성자가 아닙니다.'); location.href ='/mysite/board?a=list'</script>");
			out.flush();
			out.close();
			return;
		}
		
		BoardDao dao=new BoardDao();
		dao.delete(no, writer_no);
		
		HttpUtil.redirect(response, "/mysite/board?a=list");
		
	}

}
