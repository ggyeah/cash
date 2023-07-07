<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="container">
${word} 
<table border="1">
	<tr>
		<th>날짜</th>
		<th>가격</th>
		<th>메모</th>
		<th>작성일</th>
		<th>수정일</th>
	</tr>
	<c:forEach items="${hoList}" var="c">
		<tr>
			<td>${c.cashbookDate}</td>
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
	<div>
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