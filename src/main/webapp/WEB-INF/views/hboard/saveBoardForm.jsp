<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="./../header.jsp"></jsp:include>
		<div class="container">
			<h1>게시글 작성</h1>
			<form action="saveBoard" method="post">
				<div class="input-group mb-3">
				  <span class="input-group-text">글 제목</span>
				  <input type="text" class="form-control" name="title" placeholder="글 제목을 입력하세요 ...">
				</div>
				
				<div class="input-group mb-3">
				  <span class="input-group-text">작성자</span>
				  <input type="text" class="form-control" name="writer" placeholder="작성자를 입력하세요 ...">
				</div>
				
				<div class="mb-3">
					<label for="content">내용:</label>
					<textarea class="form-control" rows="5" id="content" name="content" placeholder="내용을 입력하세요 ..."></textarea>
				</div>
				
				<button type="submit" class="btn btn-primary" >저장</button>
				
			</form>
			
		</div>
	<jsp:include page="./../footer.jsp"></jsp:include>
</body>
</html>