<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>T.Škobić - zadaća 2. - pregled svih aerodroma</title>
</head>
<body>
	<h1>Pregled svih aerodroma</h1>
	<a href="${pageContext.servletContext.contextPath}/mvc/aerodromi/pocetak">Početak</a>
	<br>
	<table border="1">
		<tr>
			<th>ICAO</th>
			<th>Naziv</th>
			<th>Država</th>
		</tr>
		<c:forEach var="a" items="${requestScope.aerodromi}">
			<tr>
				<td>${a.icao}</td>
				<td>${a.naziv}</td>
				<td>${a.drzava}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>