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
<script type="text/javascript">
	
	function deleteOK(post_no, writer_no, loginMember_no) {
		
		if(writer_no==loginMember_no){
			alert("삭제합니다.");
			location.href = "/mysite/board?a=delete&no="+post_no+"&member_no="+writer_no;
			return true;
		}
		else{
			alert("삭제 실패");
			return false;
		}
	}

</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value="">
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
				
				<c:set var="countTotal" value="${fn:length(list) }"/>
				<c:forEach items="${list }" varStatus="i">
						<tr>
							<td>${countTotal-i.index}</td> <!-- 글번호 -->
							<td><a href="/mysite/board?a=view&no=${list.get(i.index).get(0)}">${list.get(i.index).get(1)}</a></td> <!-- 글제목 -->
							<td>${list.get(i.index).get(3)}</td> <!-- 글쓴이 -->
							<td>${list.get(i.index).get(4)}</td> <!-- 조회수 -->
							<td>${list.get(i.index).get(5)}</td> <!-- 등록일 -->
							
							<td><a href="#" class="del" 
									onclick="return deleteOK(${list.get(i.index).get(0)}, ${list.get(i.index).get(2)}, ${loginMember_no})">삭제</a></td>
						</tr>
				</c:forEach>
								
				</table>
				<div class="pager">
					<ul>
						<li class="pg-prev"><a href="#">◀ 이전</a></li>
						<li><a href="#">1</a></li>
						<li><a href="#">2</a></li>
						<li><a href="#">3</a></li>
						<li class="disable">4</li>
						<li class="disable">5</li>
						<li class="pg-next"><a href="#">다음 ▶</a></li>
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