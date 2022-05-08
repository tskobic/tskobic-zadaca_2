package org.foi.nwtis.tskobic.zadaca_2.mvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.podaci.Icao;
import org.foi.nwtis.rest.podaci.AvionLeti;
import org.foi.nwtis.tskobic.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;

import com.google.gson.Gson;

import jakarta.servlet.ServletContext;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

/**
 * Klasa AerodromiKlijent koja preuzima podatke od REST APIa.
 */
public class AerodromiKlijent {
	
	/** postavke baze podataka. */
	PostavkeBazaPodataka konfig;
	
	/**
	 * Konstruktor.
	 *
	 * @param context kontekst servleta
	 */
	public AerodromiKlijent(ServletContext context) {
		this.konfig = (PostavkeBazaPodataka) context.getAttribute("Postavke");
	}
	
	/**
	 * Vraća sve aerodrome.
	 *
	 * @return the lista aerodroma
	 */
	public List<Aerodrom> dajSveAerodrome() {
		Client client = ClientBuilder.newClient();

		String adresa = konfig.dajPostavku("adresa.wa_1");
		WebTarget webResource = client.target(adresa + "/aerodromi");
		Response restOdgovor = webResource.request().header("Accept", "application/json").get();
		List<Aerodrom> aerodromi = null;
		if (restOdgovor.getStatus() == 200) {
			String odgovor = restOdgovor.readEntity(String.class);
			Gson gson = new Gson();
			aerodromi = new ArrayList<>();
			aerodromi.addAll(Arrays.asList(gson.fromJson(odgovor, Aerodrom[].class)));
		}
		return aerodromi;
	}
	
	/**
	 * Vraća praćene aerodrome.
	 *
	 * @return the lista praćenih aerodroma
	 */
	public List<Aerodrom> dajPraceneAerodrome() {
		Client client = ClientBuilder.newClient();

		String adresa = konfig.dajPostavku("adresa.wa_1");
		WebTarget webResource = client.target(adresa + "/aerodromi?preuzimanje");
		Response restOdgovor = webResource.request().header("Accept", "application/json").get();
		List<Aerodrom> aerodromi = null;
		
		if (restOdgovor.getStatus() == 200) {
			String odgovor = restOdgovor.readEntity(String.class);
			Gson gson = new Gson();
			aerodromi = new ArrayList<>();
			aerodromi.addAll(Arrays.asList(gson.fromJson(odgovor, Aerodrom[].class)));
		}
		
		return aerodromi;
	}
	
	/**
	 * Vraća polaske s određenog aerodroma na određeni dan.
	 *
	 * @param icao icao aerodroma
	 * @param dan dan polaska
	 * @return the lista aviona koji su pošli sa polazišta
	 */
	public List<AvionLeti> dajAerodromPolaske(String icao, String dan) {
		Client client = ClientBuilder.newClient();
		
		String adresa = konfig.dajPostavku("adresa.wa_1");
		WebTarget webResource = client.target(adresa + "/aerodromi").path(icao).path("polasci").queryParam("dan", dan);
		Response restOdgovor = webResource.request().header("Accept", "application/json").get();
		List<AvionLeti> aerodromPolasci = null;
		
		if (restOdgovor.getStatus() == 200) {
			String odgovor = restOdgovor.readEntity(String.class);
			Gson gson = new Gson();
			aerodromPolasci = new ArrayList<>();
			aerodromPolasci.addAll(Arrays.asList(gson.fromJson(odgovor, AvionLeti[].class)));
		}
		
		return aerodromPolasci;
	}
	
	/**
	 * Vraća dolaske na određeni aerodrom na određeni dan.
	 *
	 * @param icao icao aerodroma
	 * @param dan dan dolaska
	 * @return the lista aviona koji su stigli na odredište
	 */
	public List<AvionLeti> dajAerodromDolaske(String icao, String dan) {
		Client client = ClientBuilder.newClient();
		
		String adresa = konfig.dajPostavku("adresa.wa_1");
		WebTarget webResource = client.target(adresa + "/aerodromi").path(icao).path("dolasci").queryParam("dan", dan);
		Response restOdgovor = webResource.request().header("Accept", "application/json").get();
		List<AvionLeti> aerodromDolasci = null;
		
		if (restOdgovor.getStatus() == 200) {
			String odgovor = restOdgovor.readEntity(String.class);
			Gson gson = new Gson();
			aerodromDolasci = new ArrayList<>();
			aerodromDolasci.addAll(Arrays.asList(gson.fromJson(odgovor, AvionLeti[].class)));
		}
		
		return aerodromDolasci;
	}

	/**
	 * Vraća aerodrom.
	 *
	 * @param icao icao aerodroma
	 * @return the aerodrom
	 */
	public Aerodrom dajAerodrom(String icao) {
		Client client = ClientBuilder.newClient();

		String adresa = konfig.dajPostavku("adresa.wa_1");
		WebTarget webResource = client.target(adresa + "/aerodromi").path(icao);
		Response restOdgovor = webResource.request().header("Accept", "application/json").get();
		Aerodrom aerodrom = null;
		if (restOdgovor.getStatus() == 200) {
			String odgovor = restOdgovor.readEntity(String.class);
			Gson gson = new Gson();
			aerodrom = gson.fromJson(odgovor, Aerodrom.class);
		}
		return aerodrom;
	}
	
	/**
	 * Dodaje aerodrom za praćenje.
	 *
	 * @param icao icao aerodroma
	 * @return the string
	 */
	public String dodajAerodromZaPracenje(String icao) {
		Client client = ClientBuilder.newClient();
		
		Icao sadrzaj = new Icao(icao);
		Gson gson = new Gson();
		
		String adresa = konfig.dajPostavku("adresa.wa_1");
		WebTarget webResource = client.target(adresa + "/aerodromi");
		Response restOdgovor = webResource.request().header("Accept", "application/json").post(Entity.json(gson.toJson(sadrzaj)));
		String odgovor = restOdgovor.readEntity(String.class);
		
		return odgovor;
	}

}
