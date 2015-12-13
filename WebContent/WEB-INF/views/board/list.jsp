<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="/mysite/board" method="post">
					<input type="text" id="kwd" name="kwd" value="${kwd }">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>				
				
				<c:choose>
					<c:when test="${empty authUser }">
						<c:set var="loginMember_no" value="-1"/>
					</c:when>
					<c:otherwise>
						<c:set var="loginMember_no" value="${authUser.no }"/>
					</c:otherwise>
				</c:choose>
				
				<c:set var="countTotal" value="${totalCount}"/>  <%-- 게시글 갯수 --%>
				
				<c:forEach items="${list }" var="list" varStatus="i">
						<tr>
							<td>${countTotal-(list.rnum-1)}</td> <!-- 글번호 -->
							<td><a href="/mysite/board?a=view&no=${list.no}">${list.title}</a></td> <!-- 글제목 -->
							<td>${list.member_name}</td> <!-- 글쓴이 -->
							<td>${list.view_cnt}</td> <!-- 조회수 -->
							<td>${list.reg_date}</td> <!-- 등록일 -->
							
							<td><a href="/mysite/board?a=delete&no=${list.no }&member_no=${list.member_no }" class="del">삭제</a></td>
						</tr>
						
				</c:forEach>
								
				
				</table>
				<div class="pager">
					<ul>
						
						<c:set var="p" value="${temp }"/> <%-- 페이지바 시작 번호 --%>
						
						<c:if test="${p!=1}">
								<li class="pg-prev"><a href="/mysite/board?page=${p-1}&kwd=${kwd }">◀ 이전</a></li>
						</c:if>
						
						<c:forEach begin="1" end="${totalPage}" var="i">
							<c:if test="${!(i > blockSize || p > totalPage) }">
								<c:choose> 
									<c:when test="${p!=nowPage }" > <%-- 현재 페이지가 아닐 경우 링크걸기 --%>
										<li><a href="/mysite/board?page=${p}&kwd=${kwd }">${p}</a></li>
									</c:when>
									<c:otherwise> <%-- 현재 페이지이면 색 주기 --%>
										<li><a href="/mysite/board?page=${p}&kwd=${kwd }"><font color="red">${p}</font></a></li>
									</c:otherwise>
								</c:choose>
								<c:set var="p" value="${p+1}"/>
							</c:if>
						</c:forEach>
						
						<c:if test="${p <= totalPage}">
								<li class="pg-next"><a href="/mysite/board?page=${p}&kwd=${kwd }">다음 ▶</a></li>
						</c:if>
						
					</ul>	
				</div>
				<div class="bottom">
					<a href="/mysite/board?a=writeform" id="new-book">글쓰기</a>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp">
			<c:param name="menu" value="board"></c:param>
		</c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp"/>
	</div>
</body>
</html>