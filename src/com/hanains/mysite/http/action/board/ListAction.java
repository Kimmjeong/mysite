package com.hanains.mysite.http.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hanains.http.HttpUtil;
import com.hanains.http.action.Action;
import com.hanains.mysite.dao.BoardDao;
import com.hanains.mysite.vo.BoardListVo;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//페이징 처리
		int totalCount = 0, totalPage = 0; // 총 게시물 수, 총 페이지 수
		int pageSize = 5;// 한페이지의 게시물수
		int blockSize = 3; // 페이지 바의 페이지 수
		int temp = 0; // 페이지번호
		int nowPage = 1;// 현재 페이지
		

		// 게시판에 처음들어왔을 경우 page가 없음, 그러므로 페이지를 선택했을 때에만 적용되도록
		if (request.getParameter("page") != null) { 
			nowPage = Integer.parseInt(request.getParameter("page"));
		}

		
		// 페이징 범위 계산
		int start = ((nowPage - 1) * pageSize) + 1;
		int end = start + pageSize - 1;

		//페이지바의 페이지 번호 역할
		temp = ((nowPage - 1) / blockSize) * blockSize + 1;
		
		// 검색어
		String word="";
		
		// 검색을 한경우 안한경우
		boolean search = false;
		if (request.getParameter("kwd") != null){
			search = true;
			word = request.getParameter("kwd"); 
		}
		
		System.out.println("검색어: "+word);
		
		BoardDao dao = new BoardDao();
		List<BoardListVo> totalList = dao.getList(search, word);
		List<BoardListVo> list = dao.getList(search, word, start, end, pageSize);
		
		System.out.println("페이징 검색 사이즈"+list.size());
		System.out.println("전체 검색 사이즈"+totalList.size());
		
		
		// 전체 글 갯수
		totalCount = totalList.size();
		// 페이지 수
		totalPage = (totalCount % pageSize)==0? (totalCount/pageSize) : (totalCount/pageSize)+1;
		
		System.out.println("전체 글 갯수: "+totalCount);
		System.out.println("페이지 수: "+totalPage);
		
		// 값 넘겨주기
		
		request.setAttribute("temp", temp);
		request.setAttribute("nowPage", nowPage);
		request.setAttribute("totalCount", totalCount); 
		request.setAttribute("totalPage", totalPage); 
		request.setAttribute("blockSize", blockSize);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("word", word);
		request.setAttribute("list", list);
				
		HttpUtil.forwarding(request, response, "/WEB-INF/views/board/list.jsp");
	}

}
