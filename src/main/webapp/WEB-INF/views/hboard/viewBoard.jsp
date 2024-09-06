<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript">
</script>
<title>게시글 조회</title>
</head> 
<body>
    <jsp:include page="./../header.jsp"></jsp:include>
    <div class="container">
        <h1>게시글 조회</h1>
        <c:forEach var="board" items="${boardDetailInfo}">
            <div class="boardInfo">                
                <div class="input-group mb-3">
                  <span class="input-group-text">글 번호</span>
                  <input type="text" class="form-control" id="boardNo" value="${board.boardNo}" readonly>
                </div>
            
                <div class="input-group mb-3">
                  <span class="input-group-text">글 제목</span>
                  <input type="text" class="form-control" id="title" value="${board.title}" readonly>
                </div>
                
                <div class="input-group mb-3">
                  <span class="input-group-text">작성자(이메일)</span>
                  <input type="text" class="form-control" id="writer" value="${board.writer}(${board.email})" readonly>
                </div>
                
                <div class="input-group mb-3">
                  <span class="input-group-text">작성일</span>
                  <input type="text" class="form-control" id="postDate" value="${board.postDate}" readonly>
                </div>
                
                <div class="input-group mb-3">
                  <span class="input-group-text">조회수</span>
                  <input type="text" class="form-control" id="readCount" value="${board.readCount}" readonly>
                </div>                                
                
                <div class="mb-3">
                    <label for="content">내용:</label>
                    <textarea class="form-control" rows="5" id="content" name="content" readonly>${board.content}</textarea>
                </div>
            </div>
            
            <div class="fileList">
                <c:forEach var="file" items="${board.fileList}">
                    <c:choose>
                        <c:when test="${file.thumbFileName != null}">
                            <div><img src="/resources/boardUpFiles/${file.thumbFileName}" alt="${file.originFileName}"/></div>
                        </c:when>
                        <c:otherwise>
                            <a href="/resources/boardUpFiles/${file.newFileName}">
                            <div>
                            <img src="/resources/images/noimage.png" />${file.originFileName}
                            </div>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
            
            <div class="btns">
                <button type="button" class="btn btn-primary" onclick="">수정</button>
                <button type="button" class="btn btn-warning" onclick="">삭제</button>
                <button type="button" class="btn btn-secondary" onclick="">목록보기</button>
            </div>  
        </c:forEach>
    </div>
    <jsp:include page="./../footer.jsp"></jsp:include>
</body>

</html>