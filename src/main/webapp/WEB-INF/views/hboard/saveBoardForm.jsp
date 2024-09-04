<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<title>Insert title here</title>
<script>

	let upfiles = new Array(); // 업로드되는 파일들을 저장하는 배열

	$(function() {
		// 업로드 파일 영역에 drag&drop과 관련된 이벤트 (파일의 경우 웹브라우저에서 열림)를 방지
		$(".fileUploadArea").on("dragenter dragover", function (evt) {
			evt.preventDefault(); // 기본 이벤트 캔슬
		});
	
		$(".fileUploadArea").on("drop", function(evt) {
			evt.preventDefault(); // 기본 이벤트 캔슬
			
			console.log(evt.originalEvent.dataTransfer.files);
			
			for (let file of evt.originalEvent.dataTransfer.files) {

				
				// 파일 사이즈를 검사해서 10MB가 넘게되면 파일 업로드가 안되도록 한다.
				console.log(file.size);
				if (file.size > 1024*1024*10) { // 10485760 바이트
					alert("파일 용량이 너무 큽니다. 업로드한 파일을 확인해주세요");		
				} else {
					upfiles.push(file); // 배열에 담기
					console.log(upfiles);
					
					// 업로드 호출하기 전 미리보기
// 					showPreview(file);
					
					// 해당파일을 업로드
					fileUpload(file);
				}
				
			}
			
		});
		
	});

	// 실제로 유저가 업로드한 파일을 컨트롤러단에 전송하여 저장되도록 하는 함수
	function fileUpload(file) {
		
		let fd = new FormData(); // form태그와 같은 역할의 객체 
		fd.append("file", file);

		// processData : false -> 데이터를 쿼리스트링 형태로 보내지 않겠다는 설정
		// contentType : false -> application/x-www-form-urlencoded로 보내지 않겠다는 설정
	      $.ajax({
	          url: '/hboard/upfiles', // 데이터가 송수신될 서버의 주소
	          type: "POST", // 통신 방식 (GET, POST, PUT, DELETE)
	          dataType: "json", // 수신 받을 데이터 타입 (MIME TYPE)
	          data: fd, // 보낼 데이터
	          processData : false,
	          contentType: false,
	          success: function (data) {
	        	  console.log(data);
	        	  if (data.msg == 'success') {
	        		  showPreview(file, data.newFileName);
	        		  
	        	  }
	          },
	          error: function () {},
	          complete: function () {},
	        });
		
	}
	
	
	
	// 넘겨진 file이 이미지 파일이면 미리보기 하여 출력한다.
	function showPreview(file, newFileName) {
		let imageType = ["image/jpeg", "image/png", "image/gif"];
		console.log(file.type);
		
		let fileType = file.type;
		
		if (imageType.indexOf(fileType) != -1) {
			// 이미지 파일이라면
// 			alert("이미지 파일입니다.");
			let output = `<div><img src='/resources/boardUpFiles\${newFileName}'/><span>\${file.name}</span>`;
			output += `<span><img src='/resources/images/remove.png' width='20px'; onclick='remFile(this)'
			id='\${newFileName}'/></span></div>`
			$(".preview").append(output);
			
		} else {
// 			alert("이미지 파일이 아닙니다.");
			let output = `<div><img src='/resources/images/noimage.png' width='25px'/><span>\${file.name}</span>`;
			output += `<span><img src='/resources/images/remove.png' width='20px'; onclick='remFile(this)'
			id='\${newFileName}'/></span></div>`
			$(".preview").append(output);
			
		}
	}
	 
	// 업로드한 파일을 지운다. (화면, front배열, 백엔드)
function remFile(obj) {
    console.log('지워야할 파일 이름 : ' +  $(obj).attr('id'));
    let removeFileName = $(obj).attr('id');
    
    // 파일 삭제 
    $.ajax({
        url: '/hboard/removefile', // 데이터가 송수신될 서버의 주소
        type: "POST", // 통신 방식 (GET, POST, PUT, DELETE)
        dataType: "json", // 수신 받을 데이터 타입 (MIME TYPE)
        data: {
            "removeFileName": removeFileName
        }, // 보낼 데이터
        success: function (data) {
            console.log(data);
        },
        error: function () {},
        complete: function () {}
    }); // 이 줄에서 괄호가 닫힘
}
	
	

	function validBoard(){
		// 게시글의 제목 (not null) 유효성 검사
		let result = false;
		let title = $("#title").val();
		console.log(title);
		console.log(title === ''); // true
		console.log(title.length < 1); // true
		console.log(title === null); // false

		if (title == '') {
			// 제목을 입력하지 않았을 때
			alert("제목은 반드시 입력하셔야 합니다.");
			$("#title").focus();
			
		} else {
			// 제목을 입력했을 때
			result = true;
				
		}
		
		return result;
	}

</script>
<style>
	.fileUploadArea {
		width : 100%;
		height : 200px;
		background-color: lightgray;
		text-align: center;
		line-height: 200px;
	}
</style>
</head>
<body>
	<jsp:include page="./../header.jsp"></jsp:include>
		<div class="container">
			<h1>게시글 작성</h1>
			<form action="saveBoard" method="post">
				<div class="input-group mb-3">
				  <span class="input-group-text">글 제목</span>
				  <input type="text" class="form-control" name="title" id="title" placeholder="글 제목을 입력하세요 ...">
				</div>
				
				<div class="input-group mb-3">
				  <span class="input-group-text">작성자</span>
				  <input type="text" class="form-control" name="writer" placeholder="작성자를 입력하세요 ...">
				</div>
				
				<div class="mb-3">
					<label for="content">내용:</label>
					<textarea class="form-control" rows="5" id="content" name="content" placeholder="내용을 입력하세요 ..."></textarea>
				</div>
				
				
<!-- 			업로드 파일 영역 -->
			<div class="fileUploadArea mb-3">
				<p>업로드할 파일을 요기에 드래그 드랍하세요</p>
			</div>
			
			<div class="preview"></div>
			
				<button type="submit" class="btn btn-primary" onclick="return validBoard();" >저장</button>
				
			</form>
			
		</div>
	<jsp:include page="./../footer.jsp"></jsp:include>
</body>
</html>