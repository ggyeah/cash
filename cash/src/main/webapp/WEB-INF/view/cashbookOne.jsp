<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
<body>
<div class="container">
${memberId} 
<form method="post" action="${pageContext.request.contextPath}/removeCashbook" id="Form">
<table border="1">
	<tr>
		<th>날짜</th>
		<th>가격</th>
		<th>메모</th>
		<th>작성일</th>
		<th>수정일</th>
		<th>수정</th>
		<th>삭제</th>
	</tr>
	<c:forEach items="${list}" var="c">
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
			<td><a href="${pageContext.request.contextPath}/modifyCashbook?cashbookNo=${c.cashbookNo}">수정</a></td>
			 <td><button type="button" onclick="deleteCashbook(${c.cashbookNo}, '${c.cashbookDate}')">삭제</button></td>
		</tr>
	</c:forEach>
</table>
</form>
</div>
</body>
</html>