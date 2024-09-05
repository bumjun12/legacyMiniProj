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
		      <tr onclick="location.href='/hboard/viewBoard?boardNo=${board.boardNo}';">
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
		 	 
		 	 <div>
<!-- 		 	 <img src="data:image/jpeg;base64,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"> -->
		 	 </div>
 	 
</div>
	<jsp:include page="./../footer.jsp"></jsp:include>
</body>
</html>