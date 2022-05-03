package org.foi.nwtis.tskobic.zadaca_2.podaci;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.foi.nwtis.rest.podaci.AvionLeti;
import org.foi.nwtis.tskobic.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;

public class AerodromiPolasciDAO {

	public boolean dodajAerodromPolasci(AvionLeti al, PostavkeBazaPodataka pbp) {
		String url = pbp.getServerDatabase() + pbp.getUserDatabase();
		String bpkorisnik = pbp.getUserUsername();
		String bplozinka = pbp.getUserPassword();
		String upit = "INSERT INTO AERODROMI_POLASCI "
				+ "(ICAO24, FIRSTSEEN, ESTDEPARTUREAIRPORT, LASTSEEN, ESTARRIVALAIRPORT, "
				+ "CALLSIGN, ESTDEPARTUREAIRPORTHORIZDISTANCE, ESTDEPARTUREAIRPORTVERTDISTANCE, ESTARRIVALAIRPORTHORIZDISTANCE, "
				+ "ESTARRIVALAIRPORTVERTDISTANCE, DEPARTUREAIRPORTCANDIDATESCOUNT, ARRIVALAIRPORTCANDIDATESCOUNT, STORED) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			Class.forName(pbp.getDriverDatabase(url));

			try (Connection con = DriverManager.getConnection(url, bpkorisnik, bplozinka);
					PreparedStatement s = con.prepareStatement(upit)) {

				s.setString(1, al.getIcao24());
				s.setInt(2, al.getFirstSeen());
				s.setString(3, al.getEstDepartureAirport());
				s.setInt(4, al.getLastSeen());
				s.setString(5, al.getEstArrivalAirport());
				s.setString(6, al.getCallsign());
				s.setInt(7, al.getEstDepartureAirportHorizDistance());
				s.setInt(8, al.getEstDepartureAirportVertDistance());
				s.setInt(9, al.getEstArrivalAirportHorizDistance());
				s.setInt(10, al.getEstArrivalAirportVertDistance());
				s.setInt(11, al.getDepartureAirportCandidatesCount());
				s.setInt(12, al.getArrivalAirportCandidatesCount());
				s.setTimestamp(13, (Timestamp) new Date());

				int brojAzuriranja = s.executeUpdate();

				return brojAzuriranja == 1;

			} catch (Exception ex) {
				Logger.getLogger(AerodromiPolasciDAO.class.getName()).log(Level.SEVERE, null, ex);
			}
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(AerodromiPolasciDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}
}
