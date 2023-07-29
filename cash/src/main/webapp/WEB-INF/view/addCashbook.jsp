<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>addCashbook.jsp</title>
<link rel="stylesheet" href="css/font.css" type="text/css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Do+Hyeon&family=Gowun+Batang:wght@400;700&family=Gowun+Dodum&family=Jua&family=Nanum+Gothic:wght@400;700&display=swap" rel="stylesheet"><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
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
<form method="post" action="${pageContext.request.contextPath}/addCashbook" id="Form">
	<h1 class="text-center">추가</h1>
	<table class="table table-bordered">
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
	<button type ="submit"  id="Btn"  class="btn btn-outline-secondary text-center">저장</button>
</form>
</div>
</body>
</html>