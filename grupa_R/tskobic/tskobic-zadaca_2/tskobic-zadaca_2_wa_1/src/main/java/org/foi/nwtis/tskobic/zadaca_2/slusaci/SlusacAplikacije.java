package org.foi.nwtis.tskobic.zadaca_2.slusaci;

import org.foi.nwtis.tskobic.zadaca_2.dretve.PreuzimanjeRasporedaAerodroma;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class SlusacAplikacije
 *
 */
@WebListener
public class SlusacAplikacije implements ServletContextListener {
	PreuzimanjeRasporedaAerodroma pra = null;
	
    /**
     * Default constructor. 
     */
    public SlusacAplikacije() {
    }

	@Override
	public void contextInitialized(ServletContextEvent sce) {
//		ServletContext context = sce.getServletContext();
//		String nazivDatoteke = context.getInitParameter("konfiguracija");
//		KonfiguracijaBP konfig = null;
//		String putanja = context.getRealPath("/WEB-INF") + java.io.File.separator; 
//		try {
//			konfig = new PostavkeBazaPodataka(putanja + nazivDatoteke);
//			konfig.ucitajKonfiguraciju();
//			context.setAttribute("Postavke", konfig);
//			System.out.println("UÄŤitana konfiguracijska datoteka.");
//		} catch (NeispravnaKonfiguracija e1) {
//			e1.printStackTrace();
//			return;
//		}

		pra = new PreuzimanjeRasporedaAerodroma();
		pra.start();
		ServletContextListener.super.contextInitialized(sce);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		context.removeAttribute("Postavke");
		
		if(pra != null) {
			pra.interrupt();
		}
		ServletContextListener.super.contextDestroyed(sce);
	}

}
