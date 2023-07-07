<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script>
$(document).ready(function() {
    // 시작시 id 입력 폼에 포커스
    $('#id').focus();
    
    // 유효성 체크 함수
    function validateForm() {
        let allCheck = true; // allCheck 변수 초기화

        if ($('#id').val() == '') {
            $('#idMsg').text('아이디를 입력하세요');
            $('#id').focus();
            allCheck = false;
        } else {
            $('#idMsg').text('');
        }

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

	<h1>login</h1>
		<form method="post" action="${pageContext.request.contextPath}/login" id="Form">
			<table>
                <tr>
                   <th>아이디</th>
                   <td><input type="text" name="memberId" id="id"></td>
                   <td>&nbsp;</td>
                </tr>
                <tr>
                   <th>비밀번호</th>
                   <td><input type="password" name="memberPw" id="pw"></td>
                   <td><button type="submit" id="Btn">login</button></td>
                </tr>
             </table>
          </form>
          <a href = "${pageContext.request.contextPath}/addMember">회원가입</a>
</body>
</html>