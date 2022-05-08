<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>T.Škobić - zadaća 2. - pregled svih problema</title>
</head>
<body>
	<h1>Pregled svih problema</h1>
	<a
		href="${pageContext.servletContext.contextPath}/mvc/aerodromi/pocetak">Početak</a>
	<br>
	<table border="1">
		<tr>
			<th>ICAO</th>
			<th>Opis</th>
			<th>Vrijeme</th>
		</tr>
		<c:forEach var="a" items="${requestScope.aerodromiProblemi}">
			<tr>
				<td><a
					href="${pageContext.servletContext.contextPath}/mvc/problemi/pregledSvihProblema/${a.icao}">${a.icao}</a></td>
				<td>${a.opis}</td>
				<td>${a.vrijeme}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>