package org.foi.nwtis.tskobic.zadaca_2.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.tskobic.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;
import org.foi.nwtis.tskobic.zadaca_2.podaci.AerodromProblem;
import org.foi.nwtis.tskobic.zadaca_2.podaci.AerodromiDAO;
import org.foi.nwtis.tskobic.zadaca_2.podaci.AerodromiProblemiDAO;

import jakarta.servlet.ServletContext;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Klasa RestProblemi za URI putanju problemi.
 */
@Path("problemi")
public class RestProblemi {

	/** Kontekst servleta. */
	@Context
	private ServletContext context;

	/**
	 * Daje sve probleme.
	 *
	 * @return the odgovor
	 */
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

	/**
	 * Daje probleme za određeni aerodrom.
	 *
	 * @param icao icao
	 * @return the odgovor
	 */
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

		if (!fAerodromProblemi.isEmpty()) {
			odgovor = Response.status(Response.Status.OK).entity(fAerodromProblemi).build();
		} else {
			odgovor = Response.status(Response.Status.NOT_FOUND).entity("Nema problema za aerodrom: " + icao).build();
		}

		return odgovor;
	}

	/**
	 * Briše probleme za određeni aerodrom.
	 *
	 * @param icao icao
	 * @return the odgovor
	 */
	@DELETE
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("{icao}")
	public Response obrisiProbleme(@PathParam("icao") String icao) {
		Response odgovor = null;

		PostavkeBazaPodataka konfig = (PostavkeBazaPodataka) context.getAttribute("Postavke");
		AerodromiDAO aerodromiDAO = new AerodromiDAO();
		AerodromiProblemiDAO aerodromiProblemiDAO = new AerodromiProblemiDAO();
		List<Aerodrom> aerodromi = aerodromiDAO.dohvatiSveAerodrome(konfig);
		List<AerodromProblem> aerodromiProblemi = aerodromiProblemiDAO.dohvatiSveProbleme(konfig);

		boolean status;
		String poruka = "";

		List<Aerodrom> fAerodromi = aerodromi.stream().filter(x -> x.getIcao().equals(icao))
				.collect(Collectors.toList());

		if (fAerodromi.isEmpty()) {
			status = false;
			poruka = "Uneseni aerodrom ne postoji.";
		} else {
			List<AerodromProblem> fAerodromProblemi = aerodromiProblemi.stream().filter(x -> x.getIcao().equals(icao))
					.collect(Collectors.toList());
			if (fAerodromProblemi.isEmpty()) {
				status = false;
				poruka = "Ne postoje problemi za uneseni aerodrom.";
			} else {
				status = aerodromiProblemiDAO.izbrisiProblem(icao, konfig);
				poruka = status ? "Izbrisani problemi za aerodrom: " + icao
						: "Brisanje problema za aerodrom: " + icao + " nije uspjelo";
			}
		}

		if (status) {
			odgovor = Response.status(Response.Status.OK).entity(poruka).build();
		} else {
			odgovor = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(poruka).build();
		}

		return odgovor;
	}
}
