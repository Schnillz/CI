package mKIebay_team;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Testklasse zur Implementierung des Interface auf CSV
 * 
 * @author b4 (DB, IH, MS, SS)
 *
 */
public class TestInterface_CSV {

	/**
	 * Testet Ein-/Ausgabe-Funktionen des Interfaces
	 * 
	 * @param args
	 * @throws IOException Wirft Input-/ oder Output-Fehlermeldung
	 */
	public static void main(String args[]) throws IOException {

		IDatenzugriff idaten = new Datenstrom_CSV();		
		
		File f = new File("test.csv");
		Properties props = new Properties();
		Properties props2 = new Properties();
		Properties props3 = new Properties();

		
		props.put("file",f);
		props.put(1,"1;felix;funktioniert");
		props.put(2,"2;max;funktioniert_auch");

		idaten.datenOutput(props);
		
		// -------------

		
		props2.put("file", f);
		props3 = idaten.datenInput(props2);
		
		for (Object o : props3.values()) {
			if (o instanceof String) {
				String s = (String) o;
				String[] fields = s.split(";");
				System.out.println(fields[0] + " - "+fields[1] + " - "+fields[2]);
			}
		}
	}

}
