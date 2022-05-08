package org.foi.nwtis.tskobic.zadaca_2.mvc;

import java.util.List;

import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.rest.podaci.AvionLeti;
import org.foi.nwtis.tskobic.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;
import org.foi.nwtis.tskobic.zadaca_2.podaci.AerodromProblem;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.View;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;

/**
 * Klasa kontrolerPregledProblema za putanju problemi.
 */
@Controller
@Path("problemi")
@RequestScoped
public class PregledProblema {

	/** kontekst servleta. */
	@Context
	private ServletContext context;
	
	/** model. */
	@Inject
	private Models model;

	/**
	 * Pregled svih problema.
	 */
	@GET
	@Path("pregledSvihProblema")
	@View("pregledSvihProblema.jsp")
	public void pregledSvihProblema() {
		ProblemiKlijent pk = new ProblemiKlijent(context);
		List<AerodromProblem> aerodromiProblemi = pk.dajSveProbleme();
		model.put("aerodromiProblemi", aerodromiProblemi);
	}
	
	/**
	 * Pregled problema za aerodrom.
	 *
	 * @param icao icao aerodroma
	 */
	@GET
	@Path("pregledSvihProblema/{icao}")
	@View("pregledProblema.jsp")
	public void pregledProblema(@PathParam("icao") String icao) {
		ProblemiKlijent pk = new ProblemiKlijent(context);
		List<AerodromProblem> aerodromProblemi = pk.dajProbleme(icao);
		model.put("aerodromProblemi", aerodromProblemi);
		model.put("problemICAO", icao);
	}
	
	/**
	 * Brisanje problema za aerodrom.
	 */
	@GET
	@Path("brisanjeProblema")
	@View("brisanjeProblema.jsp")
	public void brisanjeProblema() {
	}
	
	/**
	 * Ispisuje odgovor poslužitelja nakon pokušaja brisanja.
	 *
	 * @param icao icao aerodroma
	 */
	@GET
	@Path("brisanjeProblema/rezultat")
	@View("brisanjeProblemaRezultat.jsp")
	public void obradaBrisanjeProblema(@QueryParam("icao") String icao) {
		ProblemiKlijent pk = new ProblemiKlijent(context);
		String odgovor = pk.izbrisiProbleme(icao);
		model.put("problemOdgovor", odgovor);
	}
}
