<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/guestbook.css" rel="stylesheet" type="text/css">
<script type="text/javascript">

function deleteOk(ispwd) {
	
		var password=document.frm.password.value;
		
		if(ispwd==password){
			alert("삭제되었습니다.");
		}
		else{
			alert("비밀번호가 일치하지 않습니다.");
			document.frm.password.focus();
			return false;
		}
			
		return true;
	}
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp"/>
		<div id="content">
			<div id="guestbook" class="delete-form">
				<form method="post" action="/mysite/guestbook" name="frm">
					<input type="hidden" name="a" value="delete">
					
					<input type='hidden' name="no" value="${no}">
					<label>비밀번호</label>
					<input type="password" name="password">
					<input type="submit" value="확인" onclick="return deleteOk(${ispwd})">
				</form>
				<a href="/mysite/guestbook">방명록 리스트</a>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp">
			<c:param name="menu" value="guestbook"></c:param>
		</c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp"/>
	</div>
</body>
</html>