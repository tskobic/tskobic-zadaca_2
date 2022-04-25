package org.foi.nwtis.tskobic.zadaca_2.rest;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("aerodromi")
@Produces(MediaType.APPLICATION_JSON)
public class RestAerodromi {
	
	public Response dajSveAerodrome() {
		Response odgovor = null;
		return odgovor;
	}
	
	public Response dajAerodromeZaPratiti() {
		Response odgovor = null;
		return odgovor;
	}

	public Response dodajAerodromZaPratiti(String icao) {
		Response odgovor = null;
		return odgovor;
	}
	public Response dajAerodrom(String icao) {
		Response odgovor = null;
		return odgovor;
	}
}
