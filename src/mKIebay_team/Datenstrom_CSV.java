package mKIebay_team;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

/**
 * Abspeichern und Auslesen von CSV-Dateien
 * 
 * @author b4 (DB, IH, MS, SS)
 * 
 */
public class Datenstrom_CSV implements IDatenzugriff {
	/**
	 * Bereitet PropertiesObjekt CSV-gerecht auf und speichert es als CSV-Datei
	 * ab
	 * 
	 * @param props
	 *            PropertiesObjekt, das als CSV-Datei abgespeichert werden soll
	 */

	@Override
	public void datenOutput(Properties props) throws IOException {

		if (props == null)
			throw new RuntimeException("kein gueltiges Propertiesobjekt!");

		PrintWriter pw = null;

		try {
			File file = (File) props.get("file");
			if (file == null)
				throw new RuntimeException("file null");
			props.remove(0);
			file.delete();
			pw = new PrintWriter(new FileWriter(file, true));
			for (Object o : props.values()) {
				if (o instanceof String) {
					String s = (String) o;
					pw.println(s);
					pw.flush();
				}
			}

		} catch (FileNotFoundException e) {
			throw new RuntimeException("Konnte Datei nicht erzeugen");
		} catch (IOException e) {
			throw new RuntimeException("Fehler bei der Ein-/Ausgabe: " + e);
		} finally {
			if (pw != null)
				pw.close();
		}
	}

	/**
	 * Liest Strings aus CSV ein und wandelt in PropertiesObjekte um
	 * 
	 * @return ArrayList von PropertiesObjekten
	 */
	@Override
	public Properties datenInput(Properties p) throws IOException {

		if (p == null || !p.containsKey("file"))
			throw new RuntimeException("File fehlerhaft");

		File f = (File) p.get("file");
		Properties props = new Properties();
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(f));
			// erste Zeile auslesen, Zeiger springt automatisch weiter
			String line;
			while ((line = reader.readLine()) != null) {
				// zerlegt String nach ";"
				String[] fields = line.split(";");
				props.put(fields[0], line);
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Datei nicht gefunden!");
		} catch (IOException e) {
			throw new RuntimeException("Fehler bei der Ein-/Ausgabe: " + e);
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				throw new RuntimeException("Fehler beim Schliessen der Datei");
			}
		}
		return props;
	}
}
