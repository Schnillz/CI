package mKIebay_team;

import java.math.BigDecimal;

/**
 * Testklasse zu AuktionsVerwaltung
 * 
 * @author b4 (DB, IH, MS, SS)
 * 
 */
public class TestAuktionsVerwaltung {

	/**
	 * Testet das Erstellen von Auktionen im Auktionsarchiv & Bieten
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		MKIebay ebay = new MKIebay("frank","super");
		
		AuktionsVerwaltung test = ebay.getAuktionsVerwaltung();
	
		Benutzer b1 = new  Benutzer("Jürgen", "abcde", ebay);
		Benutzer b2 = new  Benutzer("Otto", "abcde", ebay);
		Benutzer b3 = new  Benutzer("Dieter", "abcde", ebay);
		Benutzer b4 = new  Benutzer("Hans", "xxx", ebay);

		Artikel a1 = new Artikel("Baum");
		Artikel a2 = new Artikel("Ferrari");
		Artikel a3 = new Artikel("Haus");
		Artikel a4 = new Artikel("Schiff");
		Artikel a5 = new Artikel("Tisch");


		test.neueAuktion(b1, a1, new BigDecimal("100"), 3);
		test.neueAuktion(b4, a2, new BigDecimal("240.0"), 1);
		try{
		test.neueAuktion(b1, a2, new BigDecimal("20.0"), 10);
		}
		catch (RuntimeException e){
			System.out.println("artikel darf nur einmal verkauft werden");
		}
		test.neueAuktion(b1, a3, new BigDecimal("15.35"), 1);
		test.neueAuktion(b4, a4, new BigDecimal("33.33"), 4);
		test.neueAuktion(b4, a5, new BigDecimal("46.0"), 100);


//		System.out.println(test.getAuktionen());

			test.biete(b4, test.getAuktion(1), new BigDecimal("109.0"));
			test.biete(b3, test.getAuktion(1), new BigDecimal("119.0"));
			test.biete(b2, test.getAuktion(1), new BigDecimal("30.0"));
			test.biete(b3, test.getAuktion(2), new BigDecimal("45.8"));
		
		
		System.out.println(test);
		System.out.println(test.getAngebote(b1));
		System.out.println(test.getAuktion(5));
		System.out.println(test.getAuktionen(b1, ebay.getSuperuser()));
		test.abbrechen(b1, test.getAuktion(3));
		test.abbrechen(b4, test.getAuktion(4));
//		test.loeschen(b4, test.getAuktion(5));

		System.out.println(test);		
	}

}
