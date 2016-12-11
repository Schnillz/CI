package mKIebay_team;

import java.io.IOException;
import java.util.Properties;

/**
 *	Interface
 *
 * @author b4 (DB, IH, MS, SS)
 *
 */
public interface IDatenzugriff {
	
	/**
	 * Methode zum speichern der Dateien
	 * @param p Properties-Objekt
	 * @throws IOException
	 */
	// schreiben
	public void datenOutput(Properties p) throws IOException;
	
	/**
	 * Methode zum Ladne der Dateien
	 * @param p Properties mit Quellpfad
	 * @return ArrayList mit Properties-Objekten
	 * @throws IOException
	 */
	// input
	public Properties datenInput(Properties p) throws IOException;
	
}
