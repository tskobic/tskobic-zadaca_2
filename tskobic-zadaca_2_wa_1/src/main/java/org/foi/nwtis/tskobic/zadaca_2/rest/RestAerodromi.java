package org.foi.nwtis.tskobic.zadaca_2.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.rest.klijenti.NwtisRestIznimka;
import org.foi.nwtis.rest.klijenti.OSKlijent;
import org.foi.nwtis.rest.podaci.AvionLeti;
import org.foi.nwtis.tskobic.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;
import org.foi.nwtis.tskobic.zadaca_2.podaci.AerodromiDAO;
import org.foi.nwtis.tskobic.zadaca_2.podaci.AerodromiPraceniDAO;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("aerodromi")
public class RestAerodromi {

	@Context
	private ServletContext context;

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response dajSveAerodrome(@Context HttpServletRequest request) {
		boolean parametar = request.getParameterMap().containsKey("preuzimanje");
		Response odgovor = null;
		PostavkeBazaPodataka konfig = (PostavkeBazaPodataka) context.getAttribute("Postavke");
		List<Aerodrom> aerodromi = null;
		if(parametar) {
			AerodromiPraceniDAO aerodromiPraceniDAO = new AerodromiPraceniDAO();
			aerodromi = aerodromiPraceniDAO.dohvatiPraceneAerodrome(konfig);
		} else {
			AerodromiDAO aerodromiDAO = new AerodromiDAO();
			aerodromi = aerodromiDAO.dohvatiSveAerodrome(konfig);
		}

		if (aerodromi != null) {
			odgovor = Response.status(Response.Status.OK).entity(aerodromi).build();
		} else {
			odgovor = Response.status(Response.Status.NOT_FOUND).entity("Nema aerodroma.").build();
		}

		return odgovor;
	}

	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response dodajAerodromZaPratiti(String sadrzaj) {
		Response odgovor = null;
		
		Gson gson = new Gson();
		JsonElement element = gson.fromJson (sadrzaj, JsonElement.class); 
		JsonObject jsonObj = element.getAsJsonObject(); 
		String icao = jsonObj.get("ICAO").getAsString(); 

		PostavkeBazaPodataka konfig = (PostavkeBazaPodataka) context.getAttribute("Postavke");
		AerodromiPraceniDAO aerodromiPraceniDAO = new AerodromiPraceniDAO();
		boolean status = aerodromiPraceniDAO.dodajAerodromZaPracenje(icao, konfig);
		
		if(status) {
			odgovor = Response.status(201).entity("Aerodrom za praćenje dodan.").build();
		} else {
			odgovor = Response.status(201).entity("Aerodrom za praćenje nije dodan.").build();
		}
		
		return odgovor;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("{icao}")
	public Response dajAerodrom(@PathParam("icao") String icao) {
		Response odgovor = null;

		// TODO promijeni metodu u dajPraceneAerodrome
		PostavkeBazaPodataka konfig = (PostavkeBazaPodataka) context.getAttribute("Postavke");
		AerodromiDAO aerodromiDAO = new AerodromiDAO();
		List<Aerodrom> aerodromi = aerodromiDAO.dohvatiSveAerodrome(konfig);

		Aerodrom aerodrom = null;
		for (Aerodrom a : aerodromi) {
			if (a.getIcao().compareTo(icao) == 0) {
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
	public Response dajPolaskeAerodoma(@PathParam("icao") String icao, @QueryParam("dan") String dan) {
		Response odgovor = null;
		
		long datum = izvrsiDatumPretvaranje(dan);
		PostavkeBazaPodataka konfig = (PostavkeBazaPodataka) context.getAttribute("Postavke");
		String korisnik = konfig.dajPostavku("OpenSkyNetwork.korisnik");
		String lozinka = konfig.dajPostavku("OpenSkyNetwork.lozinka");
		OSKlijent oSKlijent = new OSKlijent(korisnik, lozinka);
		
		List<AvionLeti> avioniPolasci = null;
		try {
			avioniPolasci = oSKlijent.getDepartures(icao, datum, datum + 86399);
		} catch (NwtisRestIznimka e) {
			e.printStackTrace();
		}
		
		if (avioniPolasci != null) {
			odgovor = Response.status(Response.Status.OK).entity(avioniPolasci).build();
		} else {
			odgovor = Response.status(Response.Status.NOT_FOUND).entity("Nema polazaka na taj dan za aerodrom: " + icao).build();
		}

		return odgovor;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("{icao}/dolasci")
	public Response dajDolaskeAerodoma(@PathParam("icao") String icao, @QueryParam("dan") String dan) {
		Response odgovor = null;
		
		long datum = izvrsiDatumPretvaranje(dan);
		PostavkeBazaPodataka konfig = (PostavkeBazaPodataka) context.getAttribute("Postavke");
		String korisnik = konfig.dajPostavku("OpenSkyNetwork.korisnik");
		String lozinka = konfig.dajPostavku("OpenSkyNetwork.lozinka");
		OSKlijent oSKlijent = new OSKlijent(korisnik, lozinka);
		
		List<AvionLeti> avioniPolasci = null;
		try {
			avioniPolasci = oSKlijent.getArrivals(icao, datum, datum + 86399);
		} catch (NwtisRestIznimka e) {
			e.printStackTrace();
		}
		
		if (avioniPolasci != null) {
			odgovor = Response.status(Response.Status.OK).entity(avioniPolasci).build();
		} else {
			odgovor = Response.status(Response.Status.NOT_FOUND).entity("Nema dolazaka na taj dan za aerodrom: " + icao).build();
		}

		return odgovor;
	}
	
	public long izvrsiDatumPretvaranje (String datum) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Date date = null;
		try {
			date = sdf.parse(datum);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long milisekunde = date.getTime();
		long sekunde = TimeUnit.MILLISECONDS.toSeconds(milisekunde);
		
		return sekunde;
	}
}
