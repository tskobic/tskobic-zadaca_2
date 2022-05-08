package org.foi.nwtis.tskobic.zadaca_2.mvc;

import java.util.List;

import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.rest.podaci.AvionLeti;
import org.foi.nwtis.tskobic.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;

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
 * Kontroler PregledAerodroma za putanju aerodromi.
 */
@Controller
@Path("aerodromi")
@RequestScoped
public class PregledAerodroma {

	/** kontekst servleta. */
	@Context
	private ServletContext context;
	
	/** model. */
	@Inject
	private Models model;

	/**
	 * Početna stranica.
	 */
	@GET
	@Path("pocetak")
	@View("index.jsp")
	public void pocetak() {
	}

	/**
	 * Pregled svih aerodroma.
	 */
	@GET
	@Path("pregledSvihAerodroma")
	@View("pregledSvihAerodroma.jsp")
	public void pregledSvihAerodroma() {
		AerodromiKlijent ak = new AerodromiKlijent(context);
		List<Aerodrom> aerodromi = ak.dajSveAerodrome();
		model.put("aerodromi", aerodromi);
	}
	
	/**
	 * Pregled pračenih aerodroma.
	 */
	@GET
	@Path("pregledPracenihAerodroma")
	@View("pregledPracenihAerodroma.jsp")
	public void pregledPracenihAerodroma() {
		AerodromiKlijent ak = new AerodromiKlijent(context);
		List<Aerodrom> aerodromi = ak.dajPraceneAerodrome();
		model.put("praceniAerodromi", aerodromi);
	}
	
	/**
	 * Pregled polazaka sa aerodroma na određeni dan.
	 *
	 * @param icao icao aerodroma
	 * @param dan dan polaska
	 */
	@GET
	@Path("pregledAerodromPolazaka/{icao}")
	@View("pregledPolasciDolasci.jsp")
	public void pregledAerodromPolazaka(@PathParam("icao") String icao, @QueryParam("dan") String dan) {
		AerodromiKlijent ak = new AerodromiKlijent(context);
		List<AvionLeti> aerodromPolasci = ak.dajAerodromPolaske(icao, dan);
		model.put("tip", "Polasci");
		model.put("icao", icao);
		model.put("dan", dan);
		model.put("podaci", aerodromPolasci);

	}
	
	/**
	 * Pregled dolazaka sa aerodroma na određeni dan.
	 *
	 * @param icao icao aerodroma
	 * @param dan dan dolaska
	 */
	@GET
	@Path("pregledAerodromDolazaka/{icao}")
	@View("pregledPolasciDolasci.jsp")
	public void pregledAerodromDolazaka(@PathParam("icao") String icao, @QueryParam("dan") String dan) {
		AerodromiKlijent ak = new AerodromiKlijent(context);
		List<AvionLeti> aerodromDolasci = ak.dajAerodromDolaske(icao, dan);
		model.put("tip", "Dolasci");
		model.put("icao", icao);
		model.put("dan", dan);
		model.put("podaci", aerodromDolasci);
	}
	
	/**
	 * Pregled jednog aerodroma.
	 *
	 * @param icao icao aerodroma
	 */
	@GET
	@Path("pregledSvihAerodroma/{icao}")
	@View("pregledJednogAerodroma.jsp")
	public void pregledJednogAerodroma(@PathParam("icao") String icao) {
		AerodromiKlijent ak = new AerodromiKlijent(context);
		Aerodrom aerodrom = ak.dajAerodrom(icao);
		model.put("aerodrom", aerodrom);
	}
	
	/**
	 * Dodavanje aerodroma za preuzimanje.
	 */
	@GET
	@Path("dodavanjeAerodromaZaPreuzimanje")
	@View("dodavanjeAerodromaZaPreuzimanje.jsp")
	public void dodavanjeAerodromaZaPreuzimanje() {
	}
	
	/**
	 * Ispisuje odgovor poslužitelja nakon pokušaja dodavanja aerodroma za preuzimanje.
	 *
	 * @param icao icao aerodroma
	 */
	@GET
	@Path("dodavanjeAerodromaZaPreuzimanje/rezultat")
	@View("dodavanjeAerodromaRezultat.jsp")
	public void obradaDodavanjaAerodroma(@QueryParam("icao") String icao) {
		AerodromiKlijent ak = new AerodromiKlijent(context);
		String odgovor = ak.dodajAerodromZaPracenje(icao);
		model.put("odgovor", odgovor);
	}
	
}
