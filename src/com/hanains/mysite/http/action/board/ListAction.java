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
import com.hanains.mysite.vo.BoardListVo;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		Long totalCount = 0L;
		//페이징 처리
		Long totalPage = 0L; // 총 게시물 수, 총 페이지 수
		Long pageSize = 3L;// 한페이지의 게시물수
		Long blockSize = 3L; // 페이지 바의 페이지 수
		Long temp = 0L; // 페이지번호
		Long nowPage = 1L;// 현재 페이지
		

		// 게시판에 처음들어왔을 경우 page가 없음, 그러므로 페이지를 선택했을 때에만 적용되도록
		if (request.getParameter("page") != null) { 
			nowPage = Long.parseLong(request.getParameter("page"));
		}

		
		// 페이징 범위 계산
		Long start = ((nowPage - 1) * pageSize) + 1;
		Long end = start + pageSize - 1;

		//페이지바의 페이지 번호 역할
		temp = ((nowPage - 1) / blockSize) * blockSize + 1;
		
		// 검색어
		String kwd = request.getParameter("kwd");
		
		boolean search = false;
		if (kwd != null)
			search = true;
		else {
			kwd = "";
		}	
		
		BoardDao dao = new BoardDao();
		List<BoardListVo> list = dao.getList(search, kwd, start, end);
		
		// 전체 글 갯수
		if(list.size()==0)
			totalCount=0L;
		else
			totalCount=list.get(0).getTotcnt();
		
		// 페이지 수
		totalPage = (totalCount % pageSize)==0? (totalCount/pageSize) : (totalCount/pageSize)+1;
		
		
		// 값 넘겨주기
		
		request.setAttribute("temp", temp);
		request.setAttribute("nowPage", nowPage);
		request.setAttribute("totalCount", totalCount); 
		request.setAttribute("totalPage", totalPage); 
		request.setAttribute("blockSize", blockSize);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("kwd", kwd);
		request.setAttribute("list", list);
				
		// 조회수 중복 증가 막기
		HttpSession session = request.getSession();
		session.setAttribute("read", "no");
		
		HttpUtil.forwarding(request, response, "/WEB-INF/views/board/list.jsp");
	}

}
