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
import com.hanains.mysite.vo.UserVo;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String title=request.getParameter("title");
		String content=request.getParameter("content");
		
		HttpSession session=request.getSession();
		UserVo authUser=(UserVo) session.getAttribute("authUser");

		if(authUser==null){
			HttpUtil.redirect(response, "/mysite/board?a=list");
			return;
		}
		
		Long member_no=authUser.getNo();
		
		System.out.println(title);
		System.out.println(content);
		System.out.println(member_no);
				
		BoardVo vo=new BoardVo();
		vo.setTitle(title);
		vo.setContent(content);
		vo.setMember_no(member_no);
		
		BoardDao dao=new BoardDao();
		dao.insert(vo);
		
		HttpUtil.redirect(response, "/mysite/board?a=list");
	}

}
