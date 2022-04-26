package org.foi.nwtis.tskobic.zadaca_2.dretve;

import java.util.ArrayList;
import java.util.List;

import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.rest.klijenti.NwtisRestIznimka;
import org.foi.nwtis.rest.klijenti.OSKlijent;
import org.foi.nwtis.rest.podaci.AvionLeti;
import org.foi.nwtis.rest.podaci.Lokacija;

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

	@Override
	public synchronized void start() {
		// TODO preuzmi iz konfiguracijskih podataka
		preuzimanjeOd = 1648764000;
		preuzimanjeDo = 1648850400;
		preuzimanjeVrijeme = 6 * 60 * 60;
		vrijemePauza = 20;
		vrijemeCiklusa = 300 * 1000;
		vrijemeObrade = preuzimanjeOd;

		korisnik = "xxxx";
		lozinka = "yyyy";

		oSKlijent = new OSKlijent(korisnik, lozinka);

		super.start();
	}

	@Override
	public void run() {
		while (vrijemeObrade < preuzimanjeDo) {
			// TODO preuzmi aerodrome iz tablice AERODROMI_PRACENI
			List<Aerodrom> aerodromi = new ArrayList<>();
			Aerodrom ad = new Aerodrom("LDZA", "Airport Zagreb", "HR", new Lokacija("0", "0"));
			aerodromi.add(ad);
			ad = new Aerodrom("LDVA", "Airport Varaždin", "HR", new Lokacija("0", "0"));
			aerodromi.add(ad);
			ad = new Aerodrom("EDDF", "Airport Frankfurt", "DE", new Lokacija("0", "0"));
			aerodromi.add(ad);
			ad = new Aerodrom("EDDB", "Airport Berlin", "DE", new Lokacija("0", "0"));
			aerodromi.add(ad);
			ad = new Aerodrom("LOWW", "Airport Vienna", "AT", new Lokacija("0", "0"));
			aerodromi.add(ad);

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void interrupt() {
		super.interrupt();
	}

}
