package org.foi.nwtis.tskobic.zadaca_2.mvc;

import java.util.List;

import org.foi.nwtis.podaci.Aerodrom;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.View;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Controller
@Path("aerodromi")
@RequestScoped
public class PregledAerodroma {

	@Inject
	private Models model;

	@GET
	@Path("pocetak")
	@View("index.jsp")
	public void pocetak() {
	}

	@GET
	@Path("pregledSvihAerodroma")
	@View("pregledSvihAerodroma.jsp")
	public void pregledSvihAerodroma() {
		AerodromiKlijent ak = new AerodromiKlijent();
		List<Aerodrom> aerodromi = ak.dajSveAerodrome();
		model.put("aerodromi", aerodromi);
	}
	
	@GET
	@Path("pregledPracenihAerodroma")
	@View("pregledPracenihAerodroma.jsp")
	public void pregledPracenihAerodroma() {
		AerodromiKlijent ak = new AerodromiKlijent();
		List<Aerodrom> aerodromi = ak.dajPraceneAerodrome();
		model.put("praceniAerodromi", aerodromi);
	}
	
	@GET
	@Path("pregledJednogAerodroma/{icao}")
	@View("pregledJednogAerodroma.jsp")
	public void pregledJednogAerodroma(@PathParam("icao") String icao) {
		AerodromiKlijent ak = new AerodromiKlijent();
		Aerodrom aerodrom = ak.dajAerodrom(icao);
		model.put("aerodrom", aerodrom);
	}
}
