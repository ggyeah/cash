<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>memberOne.jsp</title>
</head>
<body>
	<h1>회원정보</h1>
<table>
		<tr>
			<th>아이디<th>
			<td>${member.memberId}</td>
		</tr>
		<tr>
			<th>비밀번호<th>
			<td>${member.memberPw}</td>
		</tr>
		<tr>
			<th>가입일<th>
			<td>${member.createdate}</td>
		</tr>
		<tr>
			<th>비밀번호수정일<th>
			<td>${member.updatedate}</td>
		</tr>
	</table>
	<a href = "${pageContext.request.contextPath}/modifyMember">비밀번호수정</a>
	<a href = "${pageContext.request.contextPath}/removeMember">회원탈퇴</a>
</body>
</html>