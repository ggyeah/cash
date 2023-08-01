<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script>
function deleteCashbook(cashbookNo, cashbookDate) {
    if (confirm("정말로 삭제하시겠습니까?")) {
        $.ajax({
            url: "${pageContext.request.contextPath}/removeCashbook",
            method: "POST",
            data: { cashbookNo: cashbookNo, cashbookDate: cashbookDate },
            success: function() {
                // 삭제 성공 시 해당 항목을 동적으로 제거
                var row = document.getElementById("row_" + cashbookNo);
                if (row) {
                    row.remove();
                }
                location.reload()
            },
            error: function() {
                alert("삭제 실패");
            }
        });
    }
}
</script>
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
<!--  리스트로 반복안하고 하나만 가져오기 -->
<h2 class="text-center">${list[0].cashbookDate}</h2>
<form method="post" action="${pageContext.request.contextPath}/removeCashbook" id="Form">
<table  class="table table-bordered">
	<tr class="table-danger">
		<th>날짜</th>
		<th>가격</th>
		<th>메모</th>
		<th>작성일</th>
		<th>수정일</th>
		<th>수정</th>
		<th>삭제</th>
	</tr>
	<!-- 총수입 총지출 구하는 변수선언 -->
	<c:set var="totalIncome" value="0" />
	<c:set var="totalExpenses" value="0" />
	<c:forEach items="${list}" var="c">
		<tr>
			<td>${c.cashbookDate}</td>
		<c:if test="${c.category == '수입'}">
			<td style="color:blue;">+${c.price}</td>
			 <c:set var="totalIncome" value="${totalIncome + c.price}" />
		</c:if>
		<c:if test="${c.category == '지출'}">
			<td style="color:red;">-${c.price}</td>
			 <c:set var="totalExpenses" value="${totalExpenses + c.price}" />
		</c:if>
			<td>${c.memo}</td>
			<td>${c.createdate}</td>
			<td>${c.updatedate}</td>
			<td><a href="${pageContext.request.contextPath}/modifyCashbook?cashbookNo=${c.cashbookNo}" class="btn btn-outline-secondary btn-sm">수정</a></td>
			 <td><button type="button" onclick="deleteCashbook(${c.cashbookNo}, '${c.cashbookDate}')" class="btn btn-outline-secondary btn-sm">삭제</button></td>
		</tr>
		</c:forEach>
		<tr>
	    	<td colspan="7" class="text-center">
	        	<strong style="color:blue;">총 수입: </strong>${totalIncome} | <strong style="color:red;">총 지출: </strong>${totalExpenses}
	    	</td>
		</tr>
</table>
</form>
</div>
</body>
</html>