package mKIebay_team;

import java.io.IOException;
import java.math.BigDecimal;

import mKIebay_team.MKIebay.Superuser;

/**
 * Testklasse anhand Aktivitätsdiagramm
 * 
 * @author b4 (DB, IH, MS, SS)
 *
 */
public class TestAktDiagramm {

	/**
	 *  Testet Verkaufsablauf vom Erstellen einer MKIebay bis hin zu Beenden der Auktion
	 *  
	 * @param args
	 * @throws IOException Wirft Input-/ oder Output-Fehlermeldung
	 * @throws RuntimeException Wirft Fehlermeldung, die zur Laufzeit entsteht
	 */
	public static void main(String[] args) throws IOException, RuntimeException {
		
		MKIebay projekt = new MKIebay("frank","super");
		MKIebay.Superuser superuser = (Superuser) projekt.login("frank","super");
		
		superuser.erstellen("hans","xas");
		superuser.erstellen("ilko","xas");
		superuser.erstellen("David","xas");
		
		Benutzer A = projekt.login("hans","xas");
		Benutzer B = projekt.login("ilko","xas");
		Benutzer C = projekt.login("David", "xas");
		
		A.anbieten(new Artikel("Baum"), new BigDecimal("20.02"), 2);
		B.bieten(1, new BigDecimal("2.32"));
		C.bieten(1, new BigDecimal("3222.12"));

		A.gekauft();
		A.verkauft();	
		C.gekauft();


	}

}
