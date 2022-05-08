<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>T.Škobić - zadaća 2.</title>
</head>
<body>
	<ul>
		<li><a
			href="${pageContext.servletContext.contextPath}/mvc/aerodromi/pregledSvihAerodroma">Pregled
				svih aerodroma</a></li>
		<li><a
			href="${pageContext.servletContext.contextPath}/mvc/aerodromi/pregledPracenihAerodroma">Pregled
				praćenih aerodroma</a></li>
		<li><a
			href="${pageContext.servletContext.contextPath}/mvc/aerodromi/dodavanjeAerodromaZaPreuzimanje">Dodavanje
				aerodroma za preuzimanje</a></li>
		<li><a
			href="${pageContext.servletContext.contextPath}/mvc/problemi/pregledSvihProblema">Pregled
				svih problema</a></li>
		<li><a
			href="${pageContext.servletContext.contextPath}/mvc/problemi/brisanjeProblema">Brisanje problema od jednog aerodroma</a></li>
	</ul>
</body>
</html>