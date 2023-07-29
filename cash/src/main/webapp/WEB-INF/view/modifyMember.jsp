<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/font.css" type="text/css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Do+Hyeon&family=Gowun+Batang:wght@400;700&family=Gowun+Dodum&family=Jua&family=Nanum+Gothic:wght@400;700&display=swap" rel="stylesheet"><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/login.css" type="text/css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script>
$(document).ready(function() {
    // 시작시 id 입력 폼에 포커스
    $('#pw').focus();
    
    // 유효성 체크 함수
    function validateForm() {
        let allCheck = true; // allCheck 변수 초기화

        if ($('#pw').val() == '') {
            $('#pwMsg').text('비밀번호를 입력하세요');
            $('#pw').focus();
            allCheck = false;
        } else {
            $('#pwMsg').text('');
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
<div class="container">
	<form method="post" action="${pageContext.request.contextPath}/modifyMember" id="Form">
	<h1>비밀번호수정</h1>
	<table>
		<tr>
			<th>수정할 비밀번호를 입력하세요<th>
		</tr>
		<tr>
			<td><input type="password" name="memberPw" id="pw">
			<span id="pwMsg" class="msg"></span></td>
		</tr>
	</table>
	<button type ="submit" id="Btn">수정</button>
	</form>
</div>
</body>
</html>