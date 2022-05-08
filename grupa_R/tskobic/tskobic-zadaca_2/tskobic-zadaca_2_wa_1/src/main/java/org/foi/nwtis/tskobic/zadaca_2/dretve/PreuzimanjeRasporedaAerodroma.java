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
import org.foi.nwtis.tskobic.zadaca_2.podaci.AerodromiDolasciDAO;
import org.foi.nwtis.tskobic.zadaca_2.podaci.AerodromiPolasciDAO;
import org.foi.nwtis.tskobic.zadaca_2.podaci.AerodromiPraceniDAO;
import org.foi.nwtis.tskobic.zadaca_2.podaci.AerodromiProblemiDAO;

import jakarta.servlet.ServletContext;

/**
 * Dretva PreuzimanjeRasporedaAerodroma koja dobavlja podatke o polijetanju i slijetanju aviona.
 */
public class PreuzimanjeRasporedaAerodroma extends Thread {

	/** datum od kojeg se počinje preuzimanje. */
	long preuzimanjeOd;
	
	/** datum do kojeg se preuzimaju podaci. */
	long preuzimanjeDo;
	
	/** vrijeme za koje se preuzimaju podaci u jednom ciklusu */
	long preuzimanjeVrijeme;
	
	/** pauza između dva aerodroma */
	long vrijemePauza;
	
	/** vrijeme ciklusa. */
	long vrijemeCiklusa;
	
	/** vrijeme obrade. */
	long vrijemeObrade;
	
	/** aerodromi praceni DAO. */
	AerodromiPraceniDAO aerodromiPraceniDAO;
	
	/** aerodromi polasci DAO. */
	AerodromiPolasciDAO aerodromiPolasciDAO;
	
	/** aerodromi dolasci DAO. */
	AerodromiDolasciDAO aerodromiDolasciDAO;
	
	/** aerodromi problemi DAO. */
	AerodromiProblemiDAO aerodromiProblemiDAO;
	
	/** korisnik. */
	String korisnik;
	
	/** lozinka. */
	String lozinka;
	
	/** OpenSkyNewtork klijent. */
	OSKlijent oSKlijent;
	
	/** kontekst servleta. */
	ServletContext context;
	
	/** postavke baze podataka. */
	PostavkeBazaPodataka konfig;

	/**
	 * Konstruktor dretve
	 *
	 * @param context kontekst servleta
	 */
	public PreuzimanjeRasporedaAerodroma(ServletContext context) {
		this.context = context;
		this.konfig = (PostavkeBazaPodataka) context.getAttribute("Postavke");
	}

	/**
	 * Metoda za pokretanje dretve
	 */
	@Override
	public synchronized void start() {
		preuzimanjeOd = izvrsiDatumPretvaranje(konfig.dajPostavku("preuzimanje.od"));
		preuzimanjeDo = izvrsiDatumPretvaranje(konfig.dajPostavku("preuzimanje.do"));
		preuzimanjeVrijeme = Long.parseLong(konfig.dajPostavku("preuzimanje.vrijeme")) * 60 * 60;
		vrijemePauza = Long.parseLong(konfig.dajPostavku("preuzimanje.pauza"));
		vrijemeCiklusa = Long.parseLong(konfig.dajPostavku("ciklus.vrijeme")) * 1000;
		vrijemeObrade = preuzimanjeOd;

		aerodromiPraceniDAO = new AerodromiPraceniDAO();
		aerodromiPolasciDAO = new AerodromiPolasciDAO();
		aerodromiDolasciDAO = new AerodromiDolasciDAO();
		aerodromiProblemiDAO = new AerodromiProblemiDAO();

		korisnik = konfig.dajPostavku("OpenSkyNetwork.korisnik");
		lozinka = konfig.dajPostavku("OpenSkyNetwork.lozinka");

		oSKlijent = new OSKlijent(korisnik, lozinka);

		super.start();
	}

	/**
	 * Glavna metoda za rad dretve
	 */
	@Override
	public void run() {
		while (vrijemeObrade < preuzimanjeDo) {
			List<Aerodrom> aerodromi = aerodromiPraceniDAO.dohvatiPraceneAerodrome(konfig);

			for (Aerodrom aerodrom : aerodromi) {
				List<AvionLeti> avioniPolasci;
				try {
					avioniPolasci = oSKlijent.getDepartures(aerodrom.getIcao(), vrijemeObrade,
							vrijemeObrade + preuzimanjeVrijeme);
					if (avioniPolasci != null) {
						System.out.println("Broj letova: " + avioniPolasci.size());
						for (AvionLeti a : avioniPolasci) {
							System.out.println("Avion: " + a.getIcao24() + " Odredište: " + a.getEstArrivalAirport());
							if (a.getEstArrivalAirport() != null) {
								aerodromiPolasciDAO.dodajAerodromPolasci(a, konfig);
							}
						}
					}
				} catch (NwtisRestIznimka e) {
					aerodromiProblemiDAO.dodajProblem(aerodrom.getIcao(), "Polasci: " + e.getMessage(), konfig);
					e.printStackTrace();
				}
				System.out.println("Dolasci na aerodrom: " + aerodrom.getIcao());
				List<AvionLeti> avioniDolasci;
				try {
					avioniDolasci = oSKlijent.getArrivals(aerodrom.getIcao(), vrijemeObrade,
							vrijemeObrade + preuzimanjeVrijeme);
					if (avioniDolasci != null) {
						System.out.println("Broj letova: " + avioniDolasci.size());
						for (AvionLeti a : avioniDolasci) {
							System.out.println("Avion: " + a.getIcao24() + " Polazište: " + a.getEstDepartureAirport());
							if (a.getEstDepartureAirport() != null) {
								aerodromiDolasciDAO.dodajAerodromDolasci(a, konfig);
							}
						}
					}
				} catch (NwtisRestIznimka e) {
					aerodromiProblemiDAO.dodajProblem(aerodrom.getIcao(), "Dolasci: " + e.getMessage(), konfig);
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

	/**
	 * Metoda za prekidanje rada dretve.
	 */
	@Override
	public void interrupt() {
		super.interrupt();
	}

	/**
	 * Pretvara datum iz stringa u sekunde
	 *
	 * @param datum datum
	 * @return the long
	 */
	public long izvrsiDatumPretvaranje(String datum) {
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
