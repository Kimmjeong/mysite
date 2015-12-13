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
		Long member_no=authUser.getNo();
		
		BoardDao dao=new BoardDao();
		
		Long groupNo=-1L;
		Long orderNo=-1L;
		Long depth=0L;
		
		// 새글
		if (request.getParameter("groupNo").equals("") && request.getParameter("orderNo").equals("") && request.getParameter("depth").equals("")) {
			groupNo = dao.getGroupNo()+1;
			System.out.println(groupNo); 
			orderNo = 1L;
			depth = 0L;
			
		} else { // 답글
			
			Long parentOrderNo = Long.parseLong(request.getParameter("orderNo"));
			Long parentDepth = Long.parseLong(request.getParameter("depth"));
			
			groupNo = Long.parseLong(request.getParameter("groupNo"));
			orderNo = parentOrderNo + 1;
			depth = parentDepth + 1;
			
			dao.updateOrderNo(orderNo);
		}
		
		BoardVo vo=new BoardVo();
		vo.setTitle(title);
		vo.setContent(content);
		vo.setMember_no(member_no);
		vo.setGroup_no(groupNo);
		vo.setOrder_no(orderNo);
		vo.setDepth(depth);
		
		dao.insert(vo);
		
		HttpUtil.redirect(response, "/mysite/board?a=list");
	}

}
