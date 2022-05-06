package org.foi.nwtis.tskobic.zadaca_2.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.tskobic.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;
import org.foi.nwtis.tskobic.zadaca_2.podaci.AerodromProblem;
import org.foi.nwtis.tskobic.zadaca_2.podaci.AerodromiDAO;
import org.foi.nwtis.tskobic.zadaca_2.podaci.AerodromiPraceniDAO;
import org.foi.nwtis.tskobic.zadaca_2.podaci.AerodromiProblemiDAO;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("problemi")
public class RestProblemi {

	@Context
	private ServletContext context;

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response dajSveProbleme() {
		Response odgovor = null;

		PostavkeBazaPodataka konfig = (PostavkeBazaPodataka) context.getAttribute("Postavke");
		List<AerodromProblem> aerodromiProblemi = null;
		AerodromiProblemiDAO aerodromiProblemiDAO = new AerodromiProblemiDAO();
		aerodromiProblemi = aerodromiProblemiDAO.dohvatiSveProbleme(konfig);

		if (aerodromiProblemi != null) {
			odgovor = Response.status(Response.Status.OK).entity(aerodromiProblemi).build();
		} else {
			odgovor = Response.status(Response.Status.NOT_FOUND).entity("Nema problema.").build();
		}

		return odgovor;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("{icao}")
	public Response dajProbleme(@PathParam("icao") String icao) {
		Response odgovor = null;

		PostavkeBazaPodataka konfig = (PostavkeBazaPodataka) context.getAttribute("Postavke");
		List<AerodromProblem> aerodromiProblemi = null;
		AerodromiProblemiDAO aerodromiProblemiDAO = new AerodromiProblemiDAO();
		aerodromiProblemi = aerodromiProblemiDAO.dohvatiSveProbleme(konfig);

		List<AerodromProblem> fAerodromProblemi = aerodromiProblemi.stream().filter(x -> x.getIcao().equals(icao))
				.collect(Collectors.toList());

		if (fAerodromProblemi != null) {
			odgovor = Response.status(Response.Status.OK).entity(fAerodromProblemi).build();
		} else {
			odgovor = Response.status(Response.Status.NOT_FOUND).entity("Nema problema za aerodrom: " + icao).build();
		}

		return odgovor;
	}

	@DELETE
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("{icao}")
	public Response obrisiProbleme(@PathParam("icao") String icao) {
		Response odgovor = null;

		PostavkeBazaPodataka konfig = (PostavkeBazaPodataka) context.getAttribute("Postavke");
		AerodromiProblemiDAO aerodromiProblemiDAO = new AerodromiProblemiDAO();
		boolean status = aerodromiProblemiDAO.izbrisiProblem(icao, konfig);

		if (status) {
			odgovor = Response.status(Response.Status.OK).entity("Izbrisani problemi za aerodrom: " + icao).build();
		} else {
			odgovor = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Brisanje problema za aerodrom: " + icao + " nije uspjelo").build();
		}

		return odgovor;
	}
}
