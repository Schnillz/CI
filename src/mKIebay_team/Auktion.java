package mKIebay_team;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Properties;
import java.util.HashMap;

/**
 * Erstellt Auktionsobjekte
 * 
 * @author b4 (DB, IH, MS, SS)
 * 
 */
public class Auktion implements Serializable {

	// erzeuge Seriennummer fuer das Objekt
	private static final long serialVersionUID = 1L;
	private static int counter = 0;
	private int id;
	private Benutzer anbieter;
	private Benutzer hoechstBieter;
	private int anz_gebote;
	private BigDecimal mindestGebot;
	private Artikel artikel;
	private HashMap<Benutzer, BigDecimal> gebote;
	private Status status;

	/**
	 * Erstellt Auktion (Konstruktor 1)
	 * 
	 * @param anbieter
	 *            Benutzer, der Auktion erstellt
	 * @param artikel
	 *            Artikel, der der Auktion zugeordnet wird
	 * @param mindestGebot
	 *            Mindest-Betrag, für den Artikel verkauft wird
	 * @param anz_gebote
	 *            Anzahl der Gebote, bis Auktion beendet wird
	 */
	public Auktion(Benutzer anbieter, Artikel artikel, BigDecimal mindestGebot,
			int anz_gebote, AuktionsVerwaltung av) throws RuntimeException {

		try {
			if (anbieter != null && av != null) {
				this.setMindestGebot(mindestGebot);
				this.setArtikel(artikel);
				this.setAnz_gebote(anz_gebote);
				this.anbieter = anbieter;
				this.status = Status.aktiv;
				this.id = incCounter();
			} else
				throw new RuntimeException(
						"Auktion kann nicht ohne Auktionsverwaltung erstellt werden!");
		} catch (Exception e) {
			throw new RuntimeException("Fehler bei der Auktionserstellung");
		}
	}

	/**
	 * Erstellt ID für Auktion
	 */
	private static int incCounter() {
		return ++Auktion.counter;
	}

	/**
	 * Gibt serialVersionUID zurŸck
	 * 
	 * @return serialVersionUID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Gibt Mindestgebot zurueck
	 * 
	 * @return gefordertes MindestGebot
	 */
	public BigDecimal getMindestGebot() {
		return mindestGebot;
	}
	
	public BigDecimal getGebot(Benutzer b) {
		return gebote.get(b);
	}
	
	/**
	 * Setzt Mindestgebot
	 * 
	 * @param mindestGebot
	 *            gefordertes MindestGebot
	 */
	public void setMindestGebot(BigDecimal mindestGebot)
			throws RuntimeException {
		if (mindestGebot != null
				&& (mindestGebot.compareTo(BigDecimal.ZERO) == 1)
				&& (mindestGebot.scale() <= 2))
			this.mindestGebot = mindestGebot;

		else
			throw new RuntimeException("Eingabe ungueltig");
	}

	/**
	 * Gibt geforderte Anzahl Gebote zurueck
	 * 
	 * @return geforderte Anzahl der Gebote
	 */
	public int getAnz_gebote() {
		return anz_gebote;
	}

	/**
	 * Setzt geforderte Anzahl Gebote
	 * 
	 * @param anz_gebote
	 *            geforderte Anzahl der Gebote
	 */
	public void setAnz_gebote(int anz_gebote) throws RuntimeException {
		if (anz_gebote > 0) {
			this.anz_gebote = anz_gebote;
			gebote = new HashMap<Benutzer, BigDecimal>();
		} else
			throw new RuntimeException(
					"Anzahl der geforderten Gebote muss mindestens 1 sein!");
	}

	/**
	 * Gibt Artikel zurueck
	 * 
	 * @return Der Auktion zugeordnete Artikel
	 */
	public Artikel getArtikel() {
		return artikel;
	}

	/**
	 * Setzt Artikel
	 * 
	 * @param artikel
	 *            Artikel der Auktion
	 */
	public void setArtikel(Artikel artikel) throws RuntimeException {
		if (artikel == null)
			throw new RuntimeException("Ungueltig");
		else
			this.artikel = artikel;

	}

	/**
	 * Gibt ID zurueck
	 * 
	 * @return ID der Auktion
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gibt Anbieter zurueck
	 * 
	 * @return Anbieter der Auktion
	 */
	public Benutzer getAnbieter() {
		return anbieter;
	}

	/**
	 * Gibt bisherige Gebote zurueck
	 * 
	 * @return Array bisheriger Gebote
	 */
	public BigDecimal[] getGebote() {
		BigDecimal[] geboteList;
		geboteList = (BigDecimal[]) gebote.values().toArray();
		return geboteList;
		
	}

	/**
	 * gibt den Status der Auktion zurück
	 * 
	 * @return Status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Setzt den Status
	 * 
	 * @param s
	 *            Status
	 */
	public void setStatus(Status s) throws RuntimeException {
		if (s == null) {
			throw new RuntimeException("Status darf nicht null sein");
		}
		this.status = s;
	}
	
	/**
	 * Gibt Hoechstbieter zurueck
	 * 
	 * @return aktueller Hoechstbieter
	 */
	public Benutzer getHoechstBieter() {
		return hoechstBieter;
	}
	
	/**
	 * Setzt den Hoechstbieter
	 * 
	 * @param b Benutzer
	 */
	private void setHoechstBieter(Benutzer b){
		if(b == null)
			throw new RuntimeException("Benutzer ist null!");
		this.hoechstBieter = b;
	}

	/**
	 * Gibt Auktionsinformationen zurueck
	 * 
	 * @return Auktionsinformationen als String
	 */
	@Override
	public String toString() {

		if (this.getHoechstBieter() == null) {
			return "Auktions Id: " + this.getId() + " Anbieter: "
					+ anbieter.toString() + " max. Anzahl Gebote: "
					+ this.getAnz_gebote() + " Artikel: "
					+ this.getArtikel().toString() + "; noch keine Gebote! "
					+ "Mindestgebot: " + this.getMindestGebot();
		} else
			return "Auktions Id: " + this.getId() + " Anbieter: "
					+ anbieter.toString() + " max. Anzahl Gebote: "
					+ this.getAnz_gebote() + " Aktuelle Anzahl Gebote: "
					+ (this.gebote.size()) + " Hoechstbieter: "
					+ this.getHoechstBieter() + " HoechstGebot: "
					+ this.getHoechstesGebot() + " Artikel: "
					+ this.getArtikel().toString() + " Mindestgebot: "
					+ this.getMindestGebot();
	}

	/**
	 * Vergleicht zwei Auktionen auf Identitaet
	 * 
	 * @param o
	 *            Object, das mit Auktion verglichen werden soll
	 * @return boolean Auktion und o identisch?
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Auktion) {
			Auktion b = (Auktion) o;
			return this.getId() == b.getId();
		}
		return false;
	}

	/**
	 * Gibt hoechstes Gebot zurueck
	 * 
	 * @return aktuell hoechstes Gebot
	 */
	public BigDecimal getHoechstesGebot() {
		if(this.getHoechstBieter() == null)
			return new BigDecimal("0");
		return gebote.get(this.getHoechstBieter());
		
	}
	
	
	/**
	 * Benutzer bietet auf Auktion
	 * 
	 * @param bieter
	 *            Benutzer, der bieten will
	 * @param gebot
	 *            Betrag des Gebotes
	 */

	public void biete(Benutzer bieter, BigDecimal gebot)
			throws RuntimeException {
		try {
			if (getStatus().equals(Status.aktiv) && bieter != null
					&& !bieter.equals(getAnbieter()) && !hatGeboten(bieter)
					&& gebot != null && gebot.compareTo(BigDecimal.ZERO) == 1
					&& gebot.scale() <= 2) {
				if(this.getHoechstBieter() == null || gebot.compareTo(this.getHoechstesGebot()) == 1)
					this.setHoechstBieter(bieter);
				this.gebote.put(bieter, gebot);
				beenden();
			} else
				throw new RuntimeException("Benutzer oder Gebot ungueltig");
		} catch (RuntimeException e) {
			throw new RuntimeException("Benutzer oder Gebot ungueltig");
		}
	}

	/**
	 * Methode wandel die Auktion in ein Propertiesobjekt um
	 * 
	 * @param df
	 *            Dateiformat
	 * @return Auktion als Properties objekt
	 */
	public Properties writeToProperties(Dateiformat df) throws RuntimeException {
		if (df == null) {
			throw new RuntimeException("dateiformat ist null");
		}
		if (df.equals(Dateiformat.csv)) {
			Properties props = new Properties();
			props.put(this.getId(), (this.getId()+";"+this.getAnbieter()+
					";"+this.getHoechstBieter()+";"+this.artikel.getId()+
					";"+this.artikel.getName()+";"+this.getHoechstesGebot()+
					";"+this.getAnz_gebote()+";"+this.getMindestGebot()));
			return props;
		} else if (df.equals(Dateiformat.ser)) {
			Properties props = new Properties();
			props.put(Auktion.getSerialversionuid(), this);
			return props;
		} else
			return null;
	}

	/**
	 * Setzt Auktion auf beendet, wenn geforderte Anzahl Gebote gedeckt
	 */
	private void beenden() throws RuntimeException {
		if (gebote.size() == getAnz_gebote()) {
			try {
				if (getHoechstesGebot().compareTo(mindestGebot)== -1)
					this.setStatus(Status.abgebrochen);
				else
					this.setStatus(Status.beendet);
			} catch (RuntimeException e) {
				throw new RuntimeException("konnte nicht beendet werden");
			}
		}
	}

	/**
	 * Prüft, ob Bieter schon geboten hat
	 * 
	 * @param b
	 *            Benutzer, der überprüft werden soll
	 * @return boolean hat Benutzer schon geboten?
	 */
	public boolean hatGeboten(Benutzer b) throws RuntimeException {
		if (b == null) {
			throw new RuntimeException("Benutzer ist null");
		}
		return gebote.containsKey(b);
	}

	public void abbrechen() {	
		if (gebote.size() == 0){
			setStatus(Status.abgebrochen);
		}
		else {
			throw new RuntimeException("kann nicht abgebrochen werden");
		}
	}

}