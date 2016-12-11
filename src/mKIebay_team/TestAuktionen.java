package mKIebay_team;

import java.math.BigDecimal;

/**
 * Testklasse zu Auktion
 * 
 * @author b4 (DB, IH, MS, SS)
 * 
 */
public class TestAuktionen {

	/**
	 * Testet Erstellen von Auktionen, Bieten & Ausgeben diverser Auktionsdaten/Auktionen
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MKIebay ebay = new MKIebay("frank","super");
		
		Benutzer b = new  Benutzer("Hans", "dasdasd", ebay);
		Benutzer w = new  Benutzer("Wurst", "dadasdasda", ebay);
		Benutzer d = new  Benutzer("Dieter", "dasdasdas", ebay);
		Benutzer p = new  Benutzer("Hansi", "xxx", ebay);
		Artikel baum = new Artikel("Baum");
		Auktion a = new Auktion(b,baum,new BigDecimal("102.31"),3 ,ebay.getAuktionsVerwaltung());
		
		a.biete(w, new BigDecimal("2000.00"));
		a.biete(d, new BigDecimal("1022.12"));
		a.biete(p, new BigDecimal("102.12"));
		
		Auktion aa = new Auktion(b,baum,new BigDecimal("102.31"), 3, ebay.getAuktionsVerwaltung());
		aa.biete(w, new BigDecimal("2000.00"));
		aa.biete(d, new BigDecimal("1022.12"));
		aa.biete(p, new BigDecimal("102.12"));
		
		System.out.println(a.getHoechstBieter());
		System.out.println(a.getHoechstesGebot());
		System.out.println(a.getAnbieter());
		
		System.out.println(a.toString());
		System.out.println(aa.toString());
		
	}
	
}


