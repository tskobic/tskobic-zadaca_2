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

import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.rest.podaci.AvionLeti;
import org.foi.nwtis.rest.podaci.Lokacija;
import org.foi.nwtis.tskobic.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;

public class AerodromiDAO {

    public List<Aerodrom> dohvatiSveAerodrome(PostavkeBazaPodataka pbp) {
        String url = pbp.getServerDatabase() + pbp.getUserDatabase();
        String bpkorisnik = pbp.getUserUsername();
        String bplozinka = pbp.getUserPassword();
        String upit = "SELECT ISO_COUNTRY AS COUNTRY, IDENT AS ICAO, COORDINATES, NAME FROM PUBLIC.AIRPORTS;";

        try {
            Class.forName(pbp.getDriverDatabase(url));

            List<Aerodrom> aerodromi = new ArrayList<>();

            try (
                     Connection con = DriverManager.getConnection(url, bpkorisnik, bplozinka);
                     Statement s = con.createStatement();
                     ResultSet rs = s.executeQuery(upit)) {

                while (rs.next()) {
                    String drzava = rs.getString("COUNTRY");
                    String icao = rs.getString("ICAO");
                    String koordinate[] = rs.getString("COORDINATES").split(", ");
                    Lokacija lokacija = new Lokacija();
                    lokacija.postavi(koordinate[0], koordinate[1]);
                    String naziv = rs.getString("NAME");
                    Aerodrom a = new Aerodrom(icao, naziv, drzava, lokacija);

                    aerodromi.add(a);
                }
                return aerodromi;

            } catch (SQLException ex) {
                Logger.getLogger(AerodromiDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AerodromiDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
