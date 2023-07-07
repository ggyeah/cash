<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>addMember.jsp</title>
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
    
    // 아이디중복 확인 함수
    function checkId() {
        // 아이디 입력란 값 가져오기
        var memberId = $('#id').val();
        
        if (memberId === '') {
            $('#idMsg').text('');
            return;
        }
        
        $.ajax({
            url: '${pageContext.request.contextPath}/addMember', // 중복 확인을 수행
            type: 'POST',
            data: {
                memberId: memberId
            },
            success: function(result) {
                // 중복 확인 결과에 따라 메시지 출력
                if (result === 'available') {
                    $('#idMsg').text('사용 가능한 아이디입니다.');
                } else {
                    $('#idMsg').text('이미 사용 중인 아이디입니다.');
                }
            }
        });
    }

    
    // 시작시 아이디 중복 확인 이벤트 등록
    checkId();

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
	<h1>회원가입</h1>
	<form method="post" action="${pageContext.request.contextPath}/addMember" id="Form">
	<table>
		<tr>
			<th>아이디</th>
			<td><input type="text" name="memberId" id="id">
			<span id="idMsg" class="msg"></span></td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td><input type="password" name="memberPw" id="pw">
			<span id="pwMsg" class="msg"></span></td>
		</tr>
	</table>
	<button type ="submit"  id="Btn">회원가입</button>
	</form>
</div>
</body>
</html>