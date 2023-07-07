<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
<div class="controller">
	<h1>비밀번호수정</h1>
	<form method="post" action="${pageContext.request.contextPath}/modifyMember" id="Form">
	<h4>수정할 비밀번호를 입력하세요</h4>
	<table>
		<tr>
			<th>비밀번호<th>
			<td><input type="password" name="memberPw" id="pw">
			<span id="pwMsg" class="msg"></span></td>
		</tr>
	</table>
	<button type ="submit" id="Btn">수정</button>
	</form>
</div>
</body>
</html>