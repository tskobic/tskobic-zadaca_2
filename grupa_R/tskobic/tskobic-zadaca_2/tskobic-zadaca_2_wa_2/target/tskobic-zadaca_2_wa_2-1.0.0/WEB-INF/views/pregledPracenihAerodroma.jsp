<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>T.Škobić - zadaća 2. - pregled praćenih aerodroma</title>
</head>
<body>
	<h1>Pregled praćenih aerodroma</h1>
	<a
		href="${pageContext.servletContext.contextPath}/mvc/aerodromi/pocetak">Početak</a>
	<br>
	<table border="1">
		<tr>
			<th>ICAO</th>
			<th>Polasci</th>
			<th>Dolasci</th>
			<th>Naziv</th>
			<th>Država</th>
		</tr>
		<c:forEach var="a" items="${requestScope.praceniAerodromi}">
			<tr>
				<td><a
					href="${pageContext.servletContext.contextPath}/mvc/aerodromi/pregledSvihAerodroma/${a.icao}">${a.icao}</a></td>
				<td><form
						action="${pageContext.servletContext.contextPath}/mvc/aerodromi/pregledAerodromPolazaka/${a.icao}"
						method="GET">
						<label for="dan">Dan:</label><br>
						<input type="text" id="dan" name="dan">
						<input type="submit" value="Pregledaj">
					</form></td>
				<td><form
						action="${pageContext.servletContext.contextPath}/mvc/aerodromi/pregledAerodromDolazaka/${a.icao}"
						method="GET">
						<label for="dan">Dan:</label><br>
						<input type="text" id="dan" name="dan">
						<input type="submit" value="Pregledaj">
					</form></td>
				<td>${a.naziv}</td>
				<td>${a.drzava}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>