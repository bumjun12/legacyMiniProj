<%@page import="java.util.spi.CalendarNameProvider"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<title>게시판 목록</title>
<script>
	$(function() {
		timediffPostDate();
		
		showModal();
		
		$(".modalCloseBtn").click(function () {
			$("#myModal").hide();
		});
	})
	
	function showModal() {
		let status = '${param.status}'; // url 주소창에서 status 쿼리스트링의 값을 가져와 변수에 저장
		console.log(status);
		
		if (status == 'success') {
			// 글 저장성공 모달창
			$(".modal-body").html('<h5>글 저장에 성공하였습니다.</h5>');
			$("#myModal").show();
		} else if (status == 'fail') {
			$(".modal-body").html('<h5>글 저장에 실패하였습니다.</h5>');
			$("#myModal").show();
		}
		
		// 게시글을 불러올 때 예외가 발생한 경우
		let except = '${exception}';
		console.log(except);
		if (except == 'error') {
			$(".modal-body").html('<h2>문제가 발생해 데이터를 불러오지 못했습니다.</h2>');			
			$("#myModal").show();
		}
	}
	
	// 게시글의 글작성일을 얻어와서 2시간 이내에 작성한 글이면 new.png 이미지를 제목 옆에 출력한다.
	function timediffPostDate () {
// 		console.log($(".postDate"));
	$(".postDate").each(function (i, e) {
		console.log(i + "번째 : " + $(e).html());
		
		let postDate = new Date($(e).html()); // 글 작성일 저장 (Date객체로 변환)
		let curDate = new Date(); // 현재 날짜 시간 객체 생성
		console.log(curDate - postDate); // 밀리초
				
		let diff = (curDate - postDate) / 1000 / 60 / 60; // (시간단위)
		console.log(diff);
		
		let title = $(e).prev().prev().html();
		console.log(title);
		
		if (diff < 3) { // 3시간 이내에 작성한 글이라면
			let output = "<span><img src='/resources/images/new.png' width='25px'/></span>";
			$(e).prev().prev().html(title + output);
			
		}
		
	});
		
	}
	
</script>
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
		        <td class="postDate">${board.postDate }</td>
		        <td>${board.readCount }</td>
		      </tr>
		      </c:forEach>
		      </tbody>
 	 </table>
 	 
 	 <div><button type="button" class="btn btn-success" onclick="location.href='/hboard/saveBoard';">글쓰기</button> </div>
 	 
 	 <!-- The Modal -->
		<div class="modal" id="myModal" style="display : none;">
		  <div class="modal-dialog">
		    <div class="modal-content">
		
		      <!-- Modal Header -->
		      <div class="modal-header">
		        <h4 class="modal-title">MiniProject</h4>
		        <button type="button" class="btn-close modalCloseBtn" data-bs-dismiss="modal"></button>
		      </div>
		
		      <!-- Modal body -->
		      <div class="modal-body">
		        
		      </div>
		
		      <!-- Modal footer -->
		      <div class="modal-footer">
		        <button type="button" class="btn btn-danger modalCloseBtn" data-bs-dismiss="modal">Close</button>
		      </div>
		
		    </div>
		  </div>
		</div>
		 	 
 	 
</div>
	<jsp:include page="./../footer.jsp"></jsp:include>
</body>
</html>