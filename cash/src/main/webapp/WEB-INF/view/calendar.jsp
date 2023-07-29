<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%><!--  jstl substring 호출하기 위해 -->
<!DOCTYPE html>
<!-- JSP 컴파일 시 자바코드로 변환되는 c:...(제어문 코드) 커스텀태그 -->
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/font.css" type="text/css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Do+Hyeon&family=Gowun+Batang:wght@400;700&family=Gowun+Dodum&family=Jua&family=Nanum+Gothic:wght@400;700&display=swap" rel="stylesheet"><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body style="background: #f6f5f7;">
<!-- 변수값 or 반환값 : EL 사용   -->
<!-- 속성값대신 EL사용
	request.getAttribute("targetYear") -- $requestScope.targetYear
	(requestScope는 생략가능)
	형변환연산이 필요없다 (EL이 자동처리)
-->
<!-- 자바코드(제어문) : JSTL 사용 -->
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
    </ul>
  </div>
</nav>
<br>
<h2 class="text-center">
<a href="${pageContext.request.contextPath}/calendar?targetYear=${targetYear}&targetMonth=${targetMonth - 1}"  style="color:black;">&#11013;</a>
${targetYear}년 ${targetMonth+1}월
<a href="${pageContext.request.contextPath}/calendar?targetYear=${targetYear}&targetMonth=${targetMonth + 1}" style="color:black;">&#10145;</a>
</h2>
<h2 class="text-center">이달의 해시태그</h2>


	<div>
		<c:forEach var="m" items="${htList}">
			<a href="${pageContext.request.contextPath}/cashbookListByTag?word=${m.word}"  style="color:black;"># ${m.word}(${m.cnt})</a>
		</c:forEach>
	</div>
<table class="table table-bordered">
   <tr class="table-danger">
      <th>월</th>
      <th>화</th>
      <th>수</th>
      <th>목</th>
      <th>금</th>
      <th>토</th>
      <th>일</th>
   	</tr>
	<c:forEach var="i" begin="0" end="${totalCell - 1}" step="1">
		<c:set var="d" value="${i-beginBlank+1}">
		</c:set>
		<c:if test="${i!=0 && i%7==0}">
			<tr>
		</c:if>
		
		<c:if test="${d<1 || d > lastDate}">
			<td></td>
		</c:if>
		<c:if test="${!(d<1 || d > lastDate)}">
			<td>
				${d}
				<a class="btn btn-outline-secondary btn-sm" href="${pageContext.request.contextPath}/addCashbook?cashbookDate=${targetYear}-${targetMonth < 10 ? '0' : ''}${targetMonth+1}-${d < 10 ? '0' : ''}${d}">추가</a>
				<c:forEach var="c" items="${list}"  varStatus="status">
				<a href="${pageContext.request.contextPath}/cashbookOne?cashbookDate=${c.cashbookDate}">
                  		 <c:if test="${d == fn:substring(c.cashbookDate,8,11)}">
                      		 <div>
                          	 <c:if test="${c.category == '수입'}">
                             	 <span style="color:black;">+${c.price}</span>
                          	 </c:if>
                          	 <c:if test="${c.category == '지출'}">
                             	 <span style="color:red;">-${c.price}</span>
                         	  </c:if>
                      	 	</div>
					</c:if>
				</a>
				</c:forEach>
			</td>
		</c:if>
	</c:forEach>
</table>
</div>
</body>
</html>