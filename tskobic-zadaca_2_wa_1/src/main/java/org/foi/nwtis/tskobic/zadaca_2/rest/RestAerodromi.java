package org.foi.nwtis.tskobic.zadaca_2.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("aerodromi")
public class RestAerodromi {
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response dajSveAerodrome() {
		Response odgovor = null;
		return odgovor;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/pracenje")
	public Response dajAerodromeZaPratiti() {
		Response odgovor = null;
		return odgovor;		
	}

	@POST
	@Produces({MediaType.APPLICATION_JSON})	
	public Response dodajAerodromZaPratiti(String icao) {
		Response odgovor = null;
		return odgovor;		
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("{icao}")
	public Response dajAerodrom(String icao) {
		Response odgovor = null;
		return odgovor;		
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("{icao}/polasci")
	public Response dajPolaskeAerodoma(String icao) {
		Response odgovor = null;
		return odgovor;
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("{icao}/dolasci")
	public Response dajDolaskeAerodoma(String icao) 	{
		Response odgovor = null;
		return odgovor;
	}

}
