package mKIebay_team;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Properties;

import mKIebay_team.MKIebay.Superuser;

/**
 * Verwaltet Auktionen
 * 
 * @author b4 (DB, IH, MS, SS)
 * 
 */
public class AuktionsVerwaltung implements Serializable {

	// erzeuge Seriennummer fuer das Objekt
	private static final long serialVersionUID = 1L;
	private ArrayList<Auktion> auktionen = new ArrayList<Auktion>();

	/**
	 * Getter für die SER-id
	 * 
	 * @return SER ID
	 */

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Gibt alle Auktionen als String aus
	 * 
	 * @return Liste aller Auktionen als String
	 */
	@Override
	public String toString() {
		String s = "";
		for (Auktion auk : auktionen) {
			s += auk.toString() + "\n";
		}
		if (s == "")
			s = "Keine Auktion vorhanden";
		return s;
	}

	/**
	 * Erstellt neue Auktion
	 * 
	 * @param benutzer
	 *            Benutzer, der Auktion einstellt
	 * @param a
	 *            Artikel, der verkauft werden soll
	 * @param mind
	 *            Mindestpreis der Auktion
	 * @param gebote
	 *            Anzahl an Bietern, bis Auktion beendet wird
	 * @throws RuntimeException
	 */
	public void neueAuktion(Benutzer benutzer, Artikel a, BigDecimal mind,
			int gebote) throws RuntimeException {
		try {
			if (a == null)
				throw new RuntimeException(
						"Benutzer oder Artikel dürfen nicht null sein!");
			for (Auktion auk : auktionen) {
				if (auk.getArtikel().equals(a))
					throw new RuntimeException(
							"man kann einem Artikel nicht mehrere Auktionen zuordnen!");
			}
			auktionen.add(new Auktion(benutzer, a, mind, gebote, this));
		} catch (RuntimeException e) {
			throw new RuntimeException("Fehle rbeim erstellen der Auktion");
		}
	}

	/**
	 * Lässt Bieter auf Auktion bieten
	 * 
	 * @param benutzer
	 *            Benutzer, der bietet
	 * @param a
	 *            Auktion, zu der ein Gebot abgegeben werden soll
	 * @param gebot
	 *            Betrag des Gebotes
	 * @throws RuntimeException
	 */
	public void biete(Benutzer benutzer, Auktion a, BigDecimal gebot)
			throws RuntimeException {
		if (a == null)
			throw new RuntimeException("diese Auktion gibt es nicht");
		try {
			a.biete(benutzer, gebot);
		} catch (RuntimeException e) {
			throw new RuntimeException("fehler beim bieten");
		}
	}

	/**
	 * Loescht Auktion
	 * 
	 * @param benutzer
	 *            Benutzer, dessen Auktion gelöscht werden soll
	 * @param a
	 *            Auktion, die gelöscht werden soll
	 * @throws RuntimeException
	 *             Falls Auktion nicht diesem Anbieter zugeordnet ist
	 */
	public void abbrechen(Benutzer benutzer, Auktion a) throws RuntimeException {
		try {
			if (a != null && benutzer != null
					&& a.getAnbieter().equals(benutzer)
					&& a.getStatus() == Status.aktiv) {
				a.abbrechen();
			} else {
				throw new RuntimeException(
						"Anbieter & Auktion stimmen nicht ueberein!");
			}
		} catch (RuntimeException e) {
			throw new RuntimeException("Fehler beim Abbrechen der Auktion");
		}
	}

	/**
	 * Gibt Auktion an Stelle i zurueck
	 * 
	 * @param i
	 *            Stelle, an der sich die Auktion in der ArrayList befindet
	 * @return Auktion an Stelle i der ArrayList auktionen
	 */
	public Auktion getAuktion(int i) {
		for (Auktion auk : auktionen) {
			if (auk.getId() == i)
				return auk;
		}
		return null;
	}

	/**
	 * Gibt ArrayList aller Auktionen zurueck
	 * 
	 * @return ArrayList auktionen
	 */
	public ArrayList<Auktion> getAuktionen(Benutzer b,Superuser s) {
		if (b.equals(s))
			return auktionen;
		ArrayList<Auktion> liste = new ArrayList<Auktion>();
		for (Auktion a: auktionen)
			if(!a.getStatus().equals(Status.abgebrochen))
				liste.add(a);
		return liste;
	}

	/**
	 * Gibt gekaufte Artikel (bzw. dazugeherige Auktionen) zurueck
	 * 
	 * @param benutzer
	 *            Benutzer, dessen abgeschlossene Auktionen (gekauft)
	 *            zurueckgegeben werden sollen
	 * @return ArrayList mit abgeschlossenen Auktionen (gekauft)
	 * @throws RuntimeException
	 */

	public ArrayList<Auktion> getGekauft(Benutzer benutzer)
			throws RuntimeException {
		if (benutzer == null) {
			throw new RuntimeException("benutzer null");
		}
		ArrayList<Auktion> auktionen = new ArrayList<Auktion>();

		for (Auktion a : this.auktionen) {
			if (a.getStatus().equals(Status.beendet)
					&& a.getHoechstBieter().equals(benutzer)) {
				auktionen.add(a);
			}
		}
		return auktionen;
	}

	/**
	 * Gibt verkaufte Artikel (bzw. dazugehörige Auktionen) zurueck
	 * 
	 * @param benutzer
	 *            Benutzer dessen abgeschlossene Auktionen (verkauft)
	 *            zurueckgegeben werden sollen
	 * @return ArrayList mit abgeschlossenen Auktionen (verkauft)
	 * @throws RuntimeException
	 */
	public ArrayList<Auktion> getVerkauft(Benutzer benutzer)
			throws RuntimeException {
		if (benutzer == null) {
			throw new RuntimeException("benutzer null");
		}
		ArrayList<Auktion> auktionen = new ArrayList<Auktion>();

		for (Auktion a : this.auktionen) {
			if (a.getStatus().equals(Status.beendet)
					&& a.getAnbieter().equals(benutzer)) {
				auktionen.add(a);
			}
		}
		return auktionen;
	}

	/**
	 * Gibt die vom Benutzer eingestellten Auktionen zurueck
	 * 
	 * @param benutzer
	 *            Benutzer, dessen Angebote zurueckgegeben werden sollen
	 * @return ArrayList mit Angeboten des Benutzers
	 * @throws RuntimeException
	 */
	public ArrayList<Auktion> getAngebote(Benutzer benutzer)
			throws RuntimeException {
		if (benutzer == null) {
			throw new RuntimeException("benutzer null");
		}
		ArrayList<Auktion> auk = new ArrayList<Auktion>();

		for (Auktion a : this.auktionen) {
			if (a.getStatus().equals(Status.aktiv)
					&& a.getAnbieter().equals(benutzer)) {
				auk.add(a);
			}
		}
		return auk;
	}

	/**
	 * Gibt Auktionen mit Geboten des Benutzers zurueck
	 * 
	 * @param benutzer
	 *            Benutzer, dessen Gebote zurueckgegeben werden sollen
	 * @return ArrayList von Auktionen mit Geboten des Benutzers
	 * @throws RuntimeException
	 */
	public ArrayList<Auktion> getGebote(Benutzer benutzer)
			throws RuntimeException {
		if (benutzer == null) {
			throw new RuntimeException("benutzer null");
		}
		ArrayList<Auktion> auktionen = new ArrayList<Auktion>();
		for (Auktion a : this.auktionen) {
			try{
			if (a.hatGeboten(benutzer) && a.getStatus().equals(Status.aktiv))
				auktionen.add(a);
			} catch (Exception e){
				throw new RuntimeException("fehler gebote");
			}
		}
		return auktionen;
	}

	/**
	 * Methode zum Speichern einer Artikelliste
	 * 
	 * @param f
	 *            Zielpfad
	 * @param a2
	 *            Auktionen
	 * @throws IOException
	 *             bei Speicherfehlern
	 * @throws RuntimeException
	 */
	public void speichern(File f, ArrayList<Auktion> a2) throws IOException,
			RuntimeException {
		try {
			if (f == null || a2 == null)
				throw new RuntimeException("file oder Arraylist null");
			Dateiformat df = getDateiformat(f);
			IDatenzugriff id = getInterface(df);

			Properties props = new Properties();

			props.put("file", f);

			if (df.equals(Dateiformat.ser)) {
				props.put(Auktion.getSerialversionuid(), a2);
			} else {
				for (Auktion a : a2) {
					props.putAll(a.writeToProperties(df));
				}
			}
			id.datenOutput(props);
		} catch (RuntimeException e) {
			throw new RuntimeException("fehler beim speichern");
		}
	}

	/**
	 * Methode zum laden einer Artikelliste
	 * 
	 * @param f
	 *            Quelle
	 * @return Liste der Auktionen
	 * @throws IOException
	 *             bei Ladefehler
	 * @throws RuntimeException
	 */
	public ArrayList<Auktion> laden(File f) throws RuntimeException {
		try {
			if (f != null) {
				Properties prop = new Properties();
				prop.put("file", f);

				IDatenzugriff id = getInterface(getDateiformat(f));

				return convertToAuktionsListe(id.datenInput(prop));
			} else
				throw new RuntimeException("fehlerhafter File");
		} catch (Exception e) {
			throw new RuntimeException("fehler beim laden");
		}
	}

	// Hilfsmethoden
	/**
	 * Uebergibt Properties-Objekt der entsprechenden Konvertierungs-Methode
	 * 
	 * @param props
	 *            Properties-Objekt mit Auktionsobjekt-Daten in CSV oder SER
	 * @return konvertiertes Auktionsobjekt
	 * @throws RuntimeException
	 */
	private ArrayList<Auktion> convertToAuktionsListe(Properties p) {
		if (p.containsKey(1L)) {
			return convertSER(p);
		} else {
			return convertCSV(p);
		}
	}

	 /**
	 * Wandelt Properties-Objekt in AuktionsArrayListe um
	 *
	 * @param props
	 * Properties-Objekt mit Auktionsobjektliste
	 * @return konvertierte Auktionsliste
	 */
	 private ArrayList<Auktion> convertSER(Properties p){
		 if (p == null)
			 return null;
		 ArrayList<Auktion> liste = new ArrayList<Auktion>();
		 try {
				@SuppressWarnings("unchecked")
				ArrayList<Auktion> aa = (ArrayList<Auktion>) p.get(1L);
				for (Auktion a : aa) {
					liste.add(getAuktion(a.getId()));
				}
				return liste;
			} catch (Exception e) {
				return null;
			}
	 }
	
	 /**
	 * Wandelt Properties-Objekt in AuktionsArrayListe um
	 *
	 * @param props
	 * Properties-Objekt mit Auktionsobjektliste
	 * @return konvertierte Auktionsliste
	 */
	 private ArrayList<Auktion> convertCSV(Properties p){
		 ArrayList<Auktion> liste = new ArrayList<Auktion>();
		 if (p == null)
			 return null;
		 for (Object o : p.keySet()) {
				if (o instanceof String) {
					try{
					int i = (Integer) Integer.valueOf((String) o);
					liste.add(getAuktion(i));
					}
					catch (NumberFormatException e){
						return null;
					}
				}
				else return null;
			}
		 return liste;
	 }

	/**
	 * Interface verbindung
	 * 
	 * @param df
	 *            Dateiformat
	 * @return das Interface
	 * @throws RuntimeException
	 */
	private IDatenzugriff getInterface(Dateiformat df) throws RuntimeException {
		if (df == null) {
			throw new RuntimeException("fehler beim Speichern");
		}
		if (df.equals(Dateiformat.csv))
			return new Datenstrom_CSV();
		else if (df.equals(Dateiformat.ser))
			return new Datenstrom_SER();
		else if (df.equals(Dateiformat.xml))
			throw new RuntimeException("XML noch nicht unterstuetzt");
		else {
			throw new RuntimeException("fehler beim Speichern");
		}
	}

	/**
	 * Methode gibt das Dateiformat des Files zurück
	 * 
	 * @param f
	 *            der File
	 * @return das Dateiformat
	 * @throws RuntimeException
	 */
	private Dateiformat getDateiformat(File f) throws RuntimeException {
		if (f == null) {
			throw new RuntimeException("fehlerhalter File");
		}
		String s = f.toString();
		String[] fields = s.split("\\.");
		String dafo = fields[1];
		
		if (dafo.equals(Dateiformat.ser.toString()))
			return Dateiformat.ser;
		else if (dafo.equals(Dateiformat.csv.toString()))
			return Dateiformat.csv;
		else if (dafo.equals(Dateiformat.xml.toString()))
			return Dateiformat.xml;
		else
			return null;
	}

}
