package mKIebay_team;

/**
 * Testklasse zu Artikel
 * 
 * @author b4 (DB, IH, MS, SS)
 */
public class TestKlasseArtikel {

	/**
	 * Testet Ausgabe und Identität von Artikeln
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Artikel a = new Artikel("Haus");
		Artikel a1 = new Artikel("Haus");
		Artikel a2 = new Artikel("Baum");
		
		System.out.println(a.toString());
		System.out.println(a1.toString());
		System.out.println(a2.toString());

		System.out.println(a.equals(a));
		System.out.println(a.equals(a1));
		System.out.println(a.equals(a2));

	}

}
