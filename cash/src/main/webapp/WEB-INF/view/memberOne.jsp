<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>memberOne.jsp</title>
<link rel="stylesheet" href="css/font.css" type="text/css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Do+Hyeon&family=Gowun+Batang:wght@400;700&family=Gowun+Dodum&family=Jua&family=Nanum+Gothic:wght@400;700&display=swap" rel="stylesheet"><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
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
	<h1 class="text-center">회원정보</h1>
<table class="table table-bordered">
		<tr>
			<th class="table-danger">아이디</th>
			<td>${member.memberId}</td>
		</tr>
		<tr>
			<th class="table-danger">가입일</th>
			<td>${member.createdate}</td>
		</tr>
		<tr>
			<th class="table-danger">비밀번호수정일</th>
			<td>${member.updatedate}</td>
		</tr>
	</table>
<a href="${pageContext.request.contextPath}/modifyMember" class="btn btn-outline-secondary btn-sm">비밀번호수정</a>
<a href="${pageContext.request.contextPath}/removeMember" class="btn btn-outline-secondary btn-sm">회원탈퇴</a>
</div>
</body>
</html>