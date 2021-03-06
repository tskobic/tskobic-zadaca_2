<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>T.Škobić - zadaća 2. - pregled problema za aerodrom ${requestScope.problemICAO}</title>
</head>
<body>
	<h1>Pregled problema za aerodrom ${requestScope.problemICAO}</h1>
	<a
		href="${pageContext.servletContext.contextPath}/mvc/aerodromi/pocetak">Početak</a>
	<br>
	<table border="1">
		<tr>
			<th>ICAO</th>
			<th>Opis</th>
			<th>Vrijeme</th>
		</tr>
		<c:forEach var="a" items="${requestScope.aerodromProblemi}">
			<tr>
				<td>${a.icao}</td>
				<td>${a.opis}</td>
				<td>${a.vrijeme}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>