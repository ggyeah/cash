<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/font.css" type="text/css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Do+Hyeon&family=Gowun+Batang:wght@400;700&family=Gowun+Dodum&family=Jua&family=Nanum+Gothic:wght@400;700&display=swap" rel="stylesheet"><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body style="background: #f6f5f7;">
<div class="container">
<nav class="navbar navbar-expand bg-white navbar-white">
  <div class="container-fluid">
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link active" href="${pageContext.request.contextPath}/memberOne">회원정보</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/logout">로그아웃</a>
      </li>
      <li class="nav-item">
        <a class="nav-link active" href="${pageContext.request.contextPath}/calendar">달력</a>
      </li>
      
    </ul>
  </div>
</nav>
<br>
<h2 class="text-center"># ${word} </h2>
<table class="table table-bordered">
	<tr class="table-danger">
		<th>날짜</th>
		<th>가격</th>
		<th>메모</th>
		<th>작성일</th>
		<th>수정일</th>
	</tr>
	<c:forEach items="${hoList}" var="c">
		<tr>
		
			<td><a href="${pageContext.request.contextPath}/cashbookOne?cashbookDate=${c.cashbookDate}">${c.cashbookDate}</a></td>
		<c:if test="${c.category == '수입'}">
			<td style="color:blue;">+${c.price}</td>
		</c:if>
		<c:if test="${c.category == '지출'}">
			<td style="color:red;">-${c.price}</td>
		</c:if>
			<td>${c.memo}</td>
			<td>${c.createdate}</td>
			<td>${c.updatedate}</td>
		</tr>
	</c:forEach>
</table>
	<div class="text-center">
        <c:forEach begin="${minPage}" end="${maxPage}" var="i">
           <c:if test="${i == currentPage}">
              <a>
              	<span>${i}</span>
              </a>
           </c:if>
		   <c:if test="${i != currentPage}">
	          <a class="page-link" href="${pageContext.request.contextPath}/cashbookListByTag?currentPage=${i}&word=${word}">
	            ${i}
	         </a>
      	   </c:if>
 		</c:forEach>
   		   <c:if test="${maxPage != lastPage}">
   	   			<a href="${pageContext.request.contextPath}/cashbookListByTag?currentPage=${minPage+pagePerPage}&word=${word}">
   	   				다음
      			</a>
   		  </c:if>
  	</div>
</div>
</body>
</html>