<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Do+Hyeon&family=Gowun+Batang:wght@400;700&family=Gowun+Dodum&family=Jua&family=Nanum+Gothic:wght@400;700&display=swap" rel="stylesheet"><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<!-- Css Styles -->
<link rel="stylesheet" href="css/login.css" type="text/css">
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
<span style="font-family: 'Jua', sans-serif;">
 현재 접속자 : ${currentCounter} 명  오늘 접속자 : ${counter} 명  누적 접속자 : ${totalCounter} 명
</span>
<div class="container" id="container">
  <div class="form-container sign-in-container">
    <form method="post" action="${pageContext.request.contextPath}/login" id="Form">
      <h1>LOGIN</h1>
      <input type="text" name="memberId" id="id" placeholder="ID" value="${savaloginId}"/>
      <input type="password" name="memberPw" id="pw" placeholder="Password" />
      <div style="font-family: 'Jua', sans-serif;">
         ID저장<input type="checkbox" name="idSave" value="y">
      </div>
      <button type="submit" id="Btn">LOGIN</button>
    </form>
  </div>
  <div class="overlay-container">
    <div class="overlay">
      <div class="overlay-panel overlay-right">
        <h1>Hello, cashbook!</h1>
        <p>Let's sign up and start a together</p>
        <a href = "${pageContext.request.contextPath}/addMember">join</a>
      </div>
    </div>
  </div>
</div>
</body>
</html>