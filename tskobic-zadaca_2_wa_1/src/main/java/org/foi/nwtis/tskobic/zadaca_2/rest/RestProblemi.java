package org.foi.nwtis.tskobic.zadaca_2.rest;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("problemi")
public class RestProblemi {
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response dajSveProbleme() {
		Response odgovor = null;
		return odgovor;
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("{icao}")
	public Response dajProbleme(@PathParam("icao") String icao) {
		Response odgovor = null;
		return odgovor;
	}
	
	@DELETE
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("{icao}")
	public Response obrisiProbleme(@PathParam("icao") String icao) {
		Response odgovor = null;
		return odgovor;
	}
}
