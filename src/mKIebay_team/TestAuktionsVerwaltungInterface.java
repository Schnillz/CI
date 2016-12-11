package mKIebay_team;

import java.io.IOException;
import java.math.BigDecimal;

import mKIebay_team.MKIebay.Superuser;

/**
 * Testklasse zu AuktionsVerwaltung mit Einbeziehung des Interfaces
 * 
 * @author b4 (DB, IH, MS, SS)
 *
 */
public class TestAuktionsVerwaltungInterface {

	/**
	 * 
	 * @param args
	 * @throws IOException Wirft Input-/ oder Output-Fehlermeldung
	 * @throws RuntimeException Wirft Fehlermeldung, die zur Laufzeit entsteht
	 */
	public static void main(String[] args) throws IOException, RuntimeException {
		
		MKIebay projekt = new MKIebay("frank","super");
		MKIebay.Superuser superuser = (Superuser) projekt.login("frank","super");
		
		superuser.erstellen("hans","xas");
		superuser.erstellen("kurt","kil");
		Benutzer A = projekt.login("hans","xas");
		Benutzer B = projekt.login("kurt","kil");
		
		A.anbieten(new Artikel("Baum"), new BigDecimal("20.02"), 1);
		B.bieten(1, new BigDecimal("20.03"));
		
		B.gekauft();
		A.verkauft();		
		
	}

}
