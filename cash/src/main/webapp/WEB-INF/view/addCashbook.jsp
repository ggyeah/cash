<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>addCashbook.jsp</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script>
$(document).ready(function() {

    // 유효성 체크 함수
    function validateForm() {
        let allCheck = true; // allCheck 변수 초기화

        if ($('input[name="category"]:checked').length === 0) {
            $('#categoryMsg').text('카테고리를 선택하세요');
            allCheck = false;
        } else {
            $('#categoryMsg').text('');
        }

        if ($('#price').val() == '') {
            $('#priceMsg').text('가격를 입력하세요');
            $('#price').focus();
            allCheck = false;
        } else {
            $('#priceMsg').text('');
        }
        
        if ($('#memo').val() == '') {
            $('#memoMsg').text('메모를 입력하세요');
            $('#memo').focus();
            allCheck = false;
        } else {
            $('#memoMsg').text('');
        }
        
        return allCheck;
    }
    
     $('#Btn').click(function(e) {
        e.preventDefault(); // 기본 동작 방지

        if (validateForm()) {
            $('#Form').submit();
        }
    });
});
</script>
</head>
<body>
<div class="controller">
<form method="post" action="${pageContext.request.contextPath}/addCashbook" id="Form">
	<table>
		<tr>
			<th>이름</th>
			<td><input type="text" name="memberId" value="${memberId}" readonly="readonly"></td>
		</tr>
		<tr>
			<th>카테고리</th>
		    <td>
		        <label><input type="radio" name="category" value="수입" id="category">수입</label>
		        <label><input type="radio" name="category" value="지출" id="category">지출</label>
		        <span id="categoryMsg" class="msg"></span>
		    </td>
		</tr>
		<tr>
			<th>날짜</th>
			<td><input type="date" name="cashbookDate" value="${cashbookDate}" readonly="readonly"></td>
		</tr>
		<tr>
			<th>가격</th>
			<td><input type="text" name="price" id="price">원
			<span id="priceMsg" class="msg"></span></td>
		</tr>
		<tr>
			<th>메모</th>
			<td><input type="text" name="memo" id="memo">
			<span id="memoMsg" class="msg"></span></td>
		</tr>
	</table>
	<button type ="submit"  id="Btn">저장</button>
</form>
</div>
</body>
</html>