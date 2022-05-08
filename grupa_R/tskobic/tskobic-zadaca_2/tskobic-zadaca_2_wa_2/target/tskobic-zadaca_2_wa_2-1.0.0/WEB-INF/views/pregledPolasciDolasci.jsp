<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>T.Škobić - zadaća 2. - ${requestScope.tip} s aerodroma ${requestScope.icao} </title>
</head>
<body>
	<h1>${requestScope.tip} - aerodrom ${requestScope.icao} na dan ${requestScope.dan}</h1>
	<a
		href="${pageContext.servletContext.contextPath}/mvc/aerodromi/pocetak">Početak</a>
	<br>
	<table border="1">
		<tr>
			<th>ICAO24</th>
			<th>First seen</th>
			<th>Est departure airport</th>
			<th>Last seen</th>
			<th>Est arrival airport</th>
			<th>Callsign</th>
			<th>Est departure airport hori distance</th>
			<th>Est departure airport vert distance</th>
			<th>Est arrival airport horiz distance</th>
			<th>Est arrival airport vert distance</th>
			<th>Departure airport candidates count</th>
			<th>Arrival airport candidates count</th>
		</tr>
		<c:forEach var="a" items="${requestScope.podaci}">
			<tr>
				<td>${a.icao24}</td>
				<td>${a.firstSeen}</td>
				<td>${a.estDepartureAirport}</td>
				<td>${a.lastSeen}</td>
				<td>${a.estArrivalAirport}</td>
				<td>${a.callsign}</td>
				<td>${a.estDepartureAirportHorizDistance}</td>
				<td>${a.estDepartureAirportVertDistance}</td>
				<td>${a.estArrivalAirportHorizDistance}</td>
				<td>${a.estArrivalAirportVertDistance}</td>
				<td>${a.departureAirportCandidatesCount}</td>
				<td>${a.arrivalAirportCandidatesCount}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>