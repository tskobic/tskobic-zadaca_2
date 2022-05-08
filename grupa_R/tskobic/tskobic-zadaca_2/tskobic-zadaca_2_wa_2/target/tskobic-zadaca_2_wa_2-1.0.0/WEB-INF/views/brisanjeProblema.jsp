<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>T.Škobić - zadaća 2. - brisanje problema</title>
</head>
<body>
	<h1>Brisanje problema</h1>
	<a
		href="${pageContext.servletContext.contextPath}/mvc/aerodromi/pocetak">Početak</a>
	<br>
	<form
		action="${pageContext.servletContext.contextPath}/mvc/problemi/brisanjeProblema/rezultat"
		method="GET">
		<label for="icao">Aerodrom ICAO:</label><br> <input type="text" id="icao"
			name="icao"> <input type="submit" value="Izbriši">
	</form>
</body>
</html>