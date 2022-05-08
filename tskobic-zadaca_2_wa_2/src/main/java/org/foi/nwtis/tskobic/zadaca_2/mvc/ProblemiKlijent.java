package org.foi.nwtis.tskobic.zadaca_2.mvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.podaci.Icao;
import org.foi.nwtis.rest.podaci.AvionLeti;
import org.foi.nwtis.tskobic.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;
import org.foi.nwtis.tskobic.zadaca_2.podaci.AerodromProblem;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import jakarta.mvc.View;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;

/**
 * Klasa ProblemiKlijent koja sadrži metode za preuzimanje podataka od REST APIa.
 */
public class ProblemiKlijent {
	
	/** postavke bazee podataka. */
	PostavkeBazaPodataka konfig;
	
	/**
	 * Konstruktor.
	 *
	 * @param context kontekst servleta
	 */
	public ProblemiKlijent(ServletContext context) {
		this.konfig = (PostavkeBazaPodataka) context.getAttribute("Postavke");
	}
	
	/**
	 * Daje sve probleme.
	 *
	 * @return the lista problema za aerodrome
	 */
	public List<AerodromProblem> dajSveProbleme() {
		Client client = ClientBuilder.newClient();

		String adresa = konfig.dajPostavku("adresa.wa_1");
		WebTarget webResource = client.target(adresa + "/problemi");
		Response restOdgovor = webResource.request().header("Accept", "application/json").get();
		List<AerodromProblem> aerodromiProblemi = null;
		if (restOdgovor.getStatus() == 200) {
			String odgovor = restOdgovor.readEntity(String.class);
			Gson gson = new Gson();
			aerodromiProblemi = new ArrayList<>();
			aerodromiProblemi.addAll(Arrays.asList(gson.fromJson(odgovor, AerodromProblem[].class)));
		}
		return aerodromiProblemi;
	}
	
	/**
	 * Daje probleme za određeni aerodrom.
	 *
	 * @param icao icao aerodroma
	 * @return the lista problema za aerodrom
	 */
	public List<AerodromProblem> dajProbleme(String icao) {
		Client client = ClientBuilder.newClient();

		String adresa = konfig.dajPostavku("adresa.wa_1");
		WebTarget webResource = client.target(adresa + "/problemi").path(icao);
		Response restOdgovor = webResource.request().header("Accept", "application/json").get();
		List<AerodromProblem> aerodromProblemi = null;
		
		if (restOdgovor.getStatus() == 200) {
			String odgovor = restOdgovor.readEntity(String.class);
			Gson gson = new Gson();
			aerodromProblemi = new ArrayList<>();
			aerodromProblemi.addAll(Arrays.asList(gson.fromJson(odgovor, AerodromProblem[].class)));
		}
		
		return aerodromProblemi;
	}
	
	/**
	 * Briše probleme za određeni aerodrom.
	 *
	 * @param icao icao aerodroma
	 * @return the string
	 */
	public String izbrisiProbleme(String icao) {
		Client client = ClientBuilder.newClient();

		String adresa = konfig.dajPostavku("adresa.wa_1");
		WebTarget webResource = client.target(adresa + "/problemi").path(icao);
		Response restOdgovor = webResource.request().header("Accept", "application/json").delete();
		String odgovor = restOdgovor.readEntity(String.class);
		
		return odgovor;
	}
}
