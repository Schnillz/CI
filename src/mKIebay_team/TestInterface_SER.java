package mKIebay_team;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Testklasse zur Implementierung des Interface auf SER
 * 
 * @author b4 (DB, IH, MS, SS)
 *
 */
public class TestInterface_SER {

	/**
	 * Testet Ein-/Ausgabe-Funktionen des Interfaces
	 * 
	 * @param args
	 * @throws IOException Wirft Input-/ oder Output-Fehlermeldung
	 */
	public static void main(String args[]) throws IOException {
		
		IDatenzugriff idaten = new Datenstrom_SER();

		ArrayList<Artikel> aSpeichern = new ArrayList<Artikel>();
		Properties pSpeichern= new Properties();
		Properties pLaden= new Properties();
	
		pSpeichern.put("file", new File("test.ser"));

		Artikel a1 = new Artikel("Baum");
		Artikel a2 = new Artikel("Blume");
		Artikel a3 = new Artikel("Auto");
		
		aSpeichern.add(a1);
		aSpeichern.add(a2);
		aSpeichern.add(a3);
		
		pSpeichern.put("liste", aSpeichern);
		
		System.out.println("Raus --> " + pSpeichern.toString());

		idaten.datenOutput(pSpeichern);
		
		pLaden.put("file", new File("test.ser"));
		Properties input = idaten.datenInput(pLaden);

		@SuppressWarnings("unchecked")
		ArrayList<Artikel> aa = (ArrayList<Artikel>) input.get("liste");
		
		for (Artikel a:aa)
		System.out.println("Rein --> " + a.toString());

	}

}
