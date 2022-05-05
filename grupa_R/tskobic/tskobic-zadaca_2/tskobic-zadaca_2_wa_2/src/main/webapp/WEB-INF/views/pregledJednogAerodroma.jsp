<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>T.Škobić - zadaća 2. - pregled jednog aerodroma</title>
</head>
<body>
	<h1>Pregled jednog aerodroma</h1>
	<a href="${pageContext.servletContext.contextPath}/mvc/aerodromi/pocetak">Početak</a>
	<br>
	<table border="1">
		<tr>
			<th>ICAO</th>
			<th>Naziv</th>
			<th>Država</th>
		</tr>
			<tr>
				<td>${requestScope.aerodrom.icao}</td>
				<td>${requestScope.aerodrom.naziv}</td>
				<td>${requestScope.aerodrom.drzava}</td>
			</tr>
	</table>
</body>
</html>