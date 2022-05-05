package org.foi.nwtis.tskobic.zadaca_2.rest;

import java.util.ArrayList;
import java.util.List;

import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.rest.podaci.Lokacija;
import org.foi.nwtis.tskobic.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;
import org.foi.nwtis.tskobic.zadaca_2.podaci.AerodromiPraceniDAO;
import org.foi.nwtis.tskobic.zadaca_2.slusaci.SlusacAplikacije;

import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("aerodromi")
public class RestAerodromi {
	
	@Context
    private ServletContext context;

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response dajSveAerodrome() {
		Response odgovor = null;

		// TODO preuzmi aerodrome iz tablice AERODROMI_PRACENI
		PostavkeBazaPodataka konfig = (PostavkeBazaPodataka) context.getAttribute("Postavke");
		AerodromiPraceniDAO aerodromiPraceniDAO = new AerodromiPraceniDAO();
		List<Aerodrom> aerodromi = aerodromiPraceniDAO.dohvatiPraceneAerodrome(konfig);

		if (aerodromi != null) {
			odgovor = Response.status(Response.Status.OK).entity(aerodromi).build();
		} else {
			odgovor = Response.status(Response.Status.NOT_FOUND).entity("Nema aerodroma.").build();
		}

		return odgovor;
	}

	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public Response dodajAerodromZaPratiti(String icao) {
		Response odgovor = null;
		return odgovor;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("{icao}")
	public Response dajAerodrom(@PathParam("icao") String icao) {
		Response odgovor = null;
		
		// TODO promijeni metodu u dajPraceneAerodrome
		PostavkeBazaPodataka konfig = (PostavkeBazaPodataka) context.getAttribute("Postavke");
		AerodromiPraceniDAO aerodromiPraceniDAO = new AerodromiPraceniDAO();
		List<Aerodrom> aerodromi = aerodromiPraceniDAO.dohvatiPraceneAerodrome(konfig);
		
		Aerodrom aerodrom = null;
		for (Aerodrom a : aerodromi) {
			if(a.getIcao().compareTo(icao) == 0) {
				aerodrom = a;
				break;
			}
		}
		
		if (aerodrom != null) {
			odgovor = Response.status(Response.Status.OK).entity(aerodrom).build();
		} else {
			odgovor = Response.status(Response.Status.NOT_FOUND).entity("Nema aerodroma: " + icao).build();
		}
		
		return odgovor;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("{icao}/polasci")
	public Response dajPolaskeAerodoma(String icao) {
		Response odgovor = null;
		return odgovor;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("{icao}/dolasci")
	public Response dajDolaskeAerodoma(String icao) {
		Response odgovor = null;
		return odgovor;
	}

}
