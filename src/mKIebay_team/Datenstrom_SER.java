package mKIebay_team;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Properties;

/**
 * Abspeichern und Auslesen von SER-Dateien
 *
 * @author b4 (DB, IH, MS, SS)
 *
 */
public class Datenstrom_SER implements IDatenzugriff, Serializable {
	
	// erzeuge Seriennummer fuer das Objekt
	private static final long serialVersionUID = 1L;
	
	/**
	 * Speichert Properties-Objekt als SER-Datei ab
	 * 
	 * @param plist PropertiesObjekt, das als SER-Datei abgespeichert werden soll
	 */
	public void datenOutput(Properties plist) throws IOException {
		
		ObjectOutputStream oos = null;
		try {		
			if (!plist.containsKey("file")) {
				throw new RuntimeException("Ungueltige Datei!");
			} else{
			File file = (File) plist.get("file");					
			file.delete();	
			plist.remove(0);
			oos = new ObjectOutputStream(new FileOutputStream(file, true));
			oos.writeObject(plist);
			}
			
		} catch (FileNotFoundException e) {
			throw new IOException("Konnte Datei nicht erzeugen");
		} catch (NullPointerException e) {
			throw new IOException("Konnte Datei nicht erzeugen");
		} catch (RuntimeException e) {
			throw new IOException("Konnte Datei nicht erzeugen");
		} catch (IOException e) {
			throw new IOException("Fehler bei der Ein-/Ausgabe: " + e);
		} finally {
			try {
				oos.close();
			} catch (Exception e) {
				throw new IOException("Fehler beim Schliessen der Datei");
			}
		}
	}

	/**
	 * Erstellt PropertiesObjekte aus SER-Datei
	 * 
	 * @return ArrayList mit PropertiesObjekten
	 */
	public Properties datenInput(Properties p) throws IOException {

		if (!p.containsKey("file"))
			throw new RuntimeException("File fehlerhaft");
		
		File f = (File) p.get("file");
		ObjectInputStream ois = null;

		try {
			ois = new ObjectInputStream(new BufferedInputStream(
					new FileInputStream(f)));
			Object o = ois.readObject();
			if (o instanceof Properties)
				return (Properties) o;
			else throw new IOException("kein Propertiesobjekt im File");
		}

		catch (EOFException e) {
			// Keine Objekte mehr in Datei, Fehler wird abgefangen
		} catch (FileNotFoundException e) {
			throw new IOException("Konnte Datei nicht lesen");
		} catch (ClassNotFoundException e) {
			throw new IOException("Konnte Datei nicht lesen");
		}catch (NullPointerException e) {
			throw new IOException("Konnte Datei nicht lesen");
		} catch (IOException e) {
			throw new IOException("Fehler bei der Ein-/Ausgabe: " + e);
			
		} finally {
			try {
				ois.close();
			} catch (Exception e) {
				throw new IOException("Fehler beim Schliessen der Datei");
			}
		}
		return null;
	}
}


