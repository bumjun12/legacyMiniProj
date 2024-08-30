<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 목록</title>
</head>
<body>
	<jsp:include page="./../header.jsp"></jsp:include>
	<div class="container">
		<h1>계층형 게시판 전체 목록</h1>
<%-- 		<div>${boardList }</div> --%>
		<!-- 테이블로 출력 -->
  <table class="table table-hover">
	    <thead>
	      <tr>
	        <th>#</th>
	        <th>글제목</th>
	        <th>작성자</th>
	        <th>작성일</th>
	        <th>조회수</th>
	      </tr>
	    </thead>
		    <tbody>
		    <c:forEach var="board" items='${boardList }'>
		      <tr>
		        <td>${board.boardNo }</td>
		        <td>${board.title }</td>
		        <td>${board.writer }</td>
		        <td>${board.postDate }</td>
		        <td>${board.readCount }</td>
		      </tr>
		      </c:forEach>
		      </tbody>
 	 </table>
 	 
 	 <div><button type="button" class="btn btn-success">글쓰기</button> </div>
 	 
 	 
 	 
 	 
	</div>
	<jsp:include page="./../footer.jsp"></jsp:include>
</body>
</html>