package org.foi.nwtis.tskobic.zadaca_2.dretve;

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
import org.foi.nwtis.tskobic.zadaca_2.podaci.AerodromiPraceniDAO;

import jakarta.servlet.ServletContext;

public class PreuzimanjeRasporedaAerodroma extends Thread {

	long preuzimanjeOd;
	long preuzimanjeDo;
	long preuzimanjeVrijeme;
	long vrijemePauza;
	long vrijemeCiklusa;
	long vrijemeObrade;
	String korisnik;
	String lozinka;
	OSKlijent oSKlijent;
	ServletContext context;
	PostavkeBazaPodataka konfig;
	
	public PreuzimanjeRasporedaAerodroma(ServletContext context) {
		this.context = context;
		this.konfig = (PostavkeBazaPodataka) context.getAttribute("Postavke");
	}

	@Override
	public synchronized void start() {
		preuzimanjeOd = izvrsiDatumPretvaranje(konfig.dajPostavku("preuzimanje.od"));
		preuzimanjeDo = izvrsiDatumPretvaranje(konfig.dajPostavku("preuzimanje.do"));
		preuzimanjeVrijeme = Long.parseLong(konfig.dajPostavku("preuzimanje.vrijeme")) * 60 * 60;
		vrijemePauza = Long.parseLong(konfig.dajPostavku("preuzimanje.pauza"));
		vrijemeCiklusa = Long.parseLong(konfig.dajPostavku("ciklus.vrijeme")) * 1000;
		vrijemeObrade = preuzimanjeOd;

		korisnik = konfig.dajPostavku("OpenSkyNetwork.korisnik");
		lozinka = konfig.dajPostavku("OpenSkyNetwork.lozinka");

		oSKlijent = new OSKlijent(korisnik, lozinka);

		super.start();
	}

	@Override
	public void run() {
		while (vrijemeObrade < preuzimanjeDo) {
			AerodromiPraceniDAO aerodromiPraceniDAO = new AerodromiPraceniDAO();
			List<Aerodrom> aerodromi = aerodromiPraceniDAO.dohvatiPraceneAerodrome(konfig);

			for (Aerodrom aerodrom : aerodromi) {
				List<AvionLeti> avioniPolasci;
				try {
					avioniPolasci = oSKlijent.getDepartures(aerodrom.getIcao(), vrijemeObrade,
							vrijemeObrade + preuzimanjeVrijeme);
					if (avioniPolasci != null) {
						System.out.println("Broj letova: " + avioniPolasci.size());
						// TODO upiši podatke u tablicu AERODROMI_POLASCI
						
						for (AvionLeti a : avioniPolasci) {
							System.out.println("Avion: " + a.getIcao24() + " Odredište: " + a.getEstArrivalAirport());
						}
					}
				} catch (NwtisRestIznimka e) {
					e.printStackTrace();
				}
				System.out.println("Dolasci na aerodrom: " + aerodrom.getIcao());
				List<AvionLeti> avioniDolasci;
				try {
					avioniDolasci = oSKlijent.getArrivals(aerodrom.getIcao(), vrijemeObrade,
							vrijemeObrade + preuzimanjeVrijeme);
					if (avioniDolasci != null) {
						System.out.println("Broj letova: " + avioniDolasci.size());
						// TODO upiši podatke u tablicu AERODROMI_DOLASCI
						
						for (AvionLeti a : avioniDolasci) {
							System.out.println("Avion: " + a.getIcao24() + " Odredište: " + a.getEstDepartureAirport());
						}
					}
				} catch (NwtisRestIznimka e) {
					e.printStackTrace();
				}
				// TODO napravi malu pauzu između 2 aerodroma
			}
			// TODO izračunaj potrebno vrijeme spavanja
			long korekcija = 0;
			long vrijemeSpavanja = vrijemeCiklusa - korekcija;
			try {
				Thread.sleep(vrijemeSpavanja);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void interrupt() {
		super.interrupt();
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
