package mKIebay_team;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Login und Benutzerverwaltung
 * 
 * @author b4 (DB, IH, MS, SS)
 * 
 */
public class MKIebay implements Serializable {

	// erzeuge Seriennummer fuer das Objekt
	private static final long serialVersionUID = 1L;
	private ArrayList<Benutzer> benutzer = new ArrayList<Benutzer>();
	private AuktionsVerwaltung aVerwaltung;
	private Superuser superuser;
//	private GUILogin guiL;
	/**
	 * Erstellt MKIebay (Konstruktor)
	 * 
	 * @param name
	 *            Name des Superusers
	 * @param pass
	 *            Passwort des Superusers
	 */
	public MKIebay(String name, String pass) throws RuntimeException {
		try {
//			if (guiLogin == null) throw new RuntimeException("LoginFehler");
//			this.guiL = guiLogin;
			superuser = new Superuser(name, pass, this);
			aVerwaltung = new AuktionsVerwaltung();
		} catch (RuntimeException e) {
			throw new RuntimeException("MKIebay konnte nicht erstellt werden");
		}
	}

	/**
	 * Administratorklasse
	 */
	public class Superuser extends Benutzer {

		// erzeuge Seriennummer fuer das Objekt
		private static final long serialVersionUID = 1L;

		/**
		 * Erstellt Superuser (Konstruktor)
		 * 
		 * @param name
		 *            Name des Superusers
		 * @param pass
		 *            Passwort des Superusers
		 * @param verwaltung
		 *            die dem Superuser zugeordnete MKIebay
		 */
		public Superuser(String name, String pass, MKIebay verwaltung)
				throws RuntimeException {
			super(name, pass, verwaltung);
		}

		/**
		 * Gibt MKIebay zurueck
		 * 
		 * @return das dem Benutzer zugeordnete MKIebay
		 */
		private MKIebay getVerwaltung() {
			return this.verwaltung;
		}

		/**
		 * Erstelle Benutzer
		 * 
		 * @param name
		 *            Name des zu erstellenden Benutzers
		 * @param pass
		 *            Passwort des zu erstellenden Benutzers
		 */
		public void erstellen(String name, String pass) throws RuntimeException {
			if (getLog()) {
				try {
					this.getVerwaltung().erstelle(name, pass);
				} catch (RuntimeException e) {
					throw new RuntimeException(
							"User konnte nicht erstellt werden");
				}
			} else
				throw new RuntimeException("nicht eingeloggt!");
		}

		/**
		 * Verbietet Superuser das Bieten
		 */
		@Override
		public void bieten(int id, BigDecimal gebot) throws RuntimeException {
			if (getLog()) {
				throw new
				RuntimeException("der Superuser darf nicht bieten!");
			} else
				throw new RuntimeException("nicht eingeloggt!");
		}
		
		/**
		 * Verbietet Superuser das Anbieten
		 */
		@Override
		public void anbieten(Artikel a, BigDecimal mind, int gebote){
			if (getLog()) {
				throw new
				RuntimeException("der Superuser darf nicht anbieten!");
			} else
				throw new RuntimeException("nicht eingeloggt!");
		}
	}

	/**
	 * Gibt Superuser zurueck
	 * 
	 * @return Superuser
	 */
	public Superuser getSuperuser() {
		return superuser;
	}

	/**
	 * getter für die Auktionsverwaltung
	 * 
	 * @return Auktionsverwaltung
	 */
	public AuktionsVerwaltung getAuktionsVerwaltung() {
		return aVerwaltung;
	}

	/**
	 * Gibt Namen der Benutzer als String zurueck
	 */
	@Override
	public String toString() {
		String s = "";
		for (Benutzer b : benutzer) {
			s += b.getName() + ", ";
		}
		if(s.equals(""))
			return "leer";
		return s.substring(0, s.length() - 2);
	}

	/**
	 * Benutzer loggt sich ein
	 * 
	 * @param name
	 *            Name des Benutzers
	 * @param pass
	 *            Passwort des Benutzers
	 * @return eingeloggter Benutzer
	 */
	public Benutzer login(String name, String pass) {
		if (superuser.getName().equals(name) && pass != null
				&& pass.equals(superuser.getPass())) {
			superuser.login();
			return superuser;
		}
		Benutzer b = suche(name);
		if (b != null && b.getPass().equals(pass)) {
			b.login();
			return b;
		}
		return null;
	}

	/**
	 * Sucht nach Benutzer
	 * 
	 * @param name
	 *            Name des gesuchten Benutzers
	 * @return gesuchter Benutzer
	 */
	public Benutzer suche(String name) {
		for (Benutzer b : benutzer) {
			if (b.getName().equals(name)) {
				return b;
			}
		}
		return null;
	}

	/**
	 * Methode zum bieten auf eine Auktion
	 * 
	 * @param benutzer2
	 *            der bietende Benutzer
	 * @param auktion
	 *            die gewählte Auktion
	 * @param gebot
	 *            das Gebot des Bieters
	 * @throws IOException
	 *             Eingabe fehler
	 * @throws RuntimeException
	 *             Laufzeit fehler
	 */
	public void biete(Benutzer benutzer2, Auktion auktion, BigDecimal gebot)
			throws RuntimeException {
		try {
			aVerwaltung.biete(benutzer2, auktion, gebot);
		} catch (RuntimeException e) {
			throw new RuntimeException("fehler beim bieten");
		}
	}

	/**
	 * Methode zum anlegen einer Auktion
	 * 
	 * @param benutzer2
	 *            Ersteller
	 * @param a
	 *            Artikel
	 * @param mind
	 *            Mindestgebot
	 * @param gebote
	 *            anzahl der Gebote
	 */
	public void neueAuktion(Benutzer benutzer2, Artikel a, BigDecimal mind,
			int gebote) throws RuntimeException {
		try {
			aVerwaltung.neueAuktion(benutzer2, a, mind, gebote);
		} catch (RuntimeException e) {
			throw new RuntimeException(
					"fehler beim erstellen eine rneuen auktion");
		}
	}

	/**
	 * Methode zum abbrechen einer Auktion
	 * 
	 * @param benutzer2
	 *            Ersteller
	 * @param auktion
	 *            Auktion
	 */

	public void abbrechen(Benutzer benutzer2, Auktion auktion)
			throws RuntimeException {
		try {
			aVerwaltung.abbrechen(benutzer2, auktion);
		} catch (RuntimeException e) {
			throw new RuntimeException("fehler deim abbrechen der Auktion");
		}
	}

	/**
	 * Sucht die auktion anhand der ID
	 * 
	 * @param id
	 *            ID der gesuchten Auktion
	 * @return die gesuchte Auktion
	 */

	public Auktion getAuktion(int id) {
		return aVerwaltung.getAuktion(id);
	}

	/**
	 * Gette rfür alle Auktionen
	 * 
	 * @return Auktionen
	 */

	public ArrayList<Auktion> getAuktionen(Benutzer b) {
		return aVerwaltung.getAuktionen(b, superuser);
	}

	/**
	 * Getter für Ersteigerungen
	 * 
	 * @param benutzer2
	 *            Ersteigerer
	 * @return Auktionen
	 */

	public ArrayList<Auktion> getGekauft(Benutzer benutzer2)
			throws RuntimeException {
		ArrayList<Auktion> a;
		try {
			a = aVerwaltung.getGekauft(benutzer2);
		} catch (RuntimeException e) {
			throw new RuntimeException("fehler bei der liste der auktionen");
		}
		return a;
	}

	/**
	 * Getter für Verkaeufe
	 * 
	 * @param benutzer2
	 *            Verkaeufer
	 * @return Auktionen
	 */
	public ArrayList<Auktion> getVerkauft(Benutzer benutzer2)
			throws RuntimeException {
		ArrayList<Auktion> a;
		try {
			a = aVerwaltung.getVerkauft(benutzer2);
		} catch (RuntimeException e) {
			throw new RuntimeException("fehler bei der liste der auktionen");
		}
		return a;
	}

	/**
	 * Getter für Angebote
	 * 
	 * @param benutzer2
	 *            Anbieter
	 * @return Auktionen
	 */
	public ArrayList<Auktion> getAngebote(Benutzer benutzer2)
			throws RuntimeException {
		ArrayList<Auktion> a;
		try {
			a = aVerwaltung.getAngebote(benutzer2);
		} catch (RuntimeException e) {
			throw new RuntimeException("fehler bei der liste der auktionen");
		}
		return a;
	}

	/**
	 * Getter für laufende Gebote
	 * 
	 * @param benutzer2
	 *            Bieter
	 * @return Auktionen
	 */
	public ArrayList<Auktion> getGebote(Benutzer benutzer2)
			throws RuntimeException {
		ArrayList<Auktion> a;
		try {
			a = aVerwaltung.getGebote(benutzer2);
		} catch (RuntimeException e) {
			throw new RuntimeException("fehler bei der liste der auktionen");
		}
		return a;
	}

	/**
	 * Methode zum Speichern einer Artikelliste
	 * 
	 * @param f
	 *            Zielpfad
	 * @param a
	 *            Auktionen
	 * @throws IOException
	 *             bei Speicherfehlern
	 */

	public void speichern(File f, ArrayList<Auktion> a) throws IOException,
			RuntimeException {
		try {
			aVerwaltung.speichern(f, a);
		} catch (Exception e) {
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
	 */
	public ArrayList<Auktion> laden(File f) throws IOException,
			RuntimeException {
		ArrayList<Auktion> a;
		try {
			a = aVerwaltung.laden(f);
		} catch (Exception e) {
			throw new RuntimeException("fehler beim Laden");
		}
		return a;
	}

	/**
	 * Benutzer erstellen
	 * 
	 * @param name
	 *            Name des zu erstellenden Benutzers
	 * @param pass
	 *            Passwort des zu erstellenden Benutzers
	 * @param archiv
	 *            die dem zu erstellenden Benutzer zugeordnete
	 *            Auktionsverwaltung
	 */
	private void erstelle(String name, String pass) throws RuntimeException {
		if (suche(name) == null)
			try {
				benutzer.add(new Benutzer(name, pass, this));
			} catch (RuntimeException e) {
				throw new RuntimeException("fehler beim user erstellen");
			}
		else
			throw new RuntimeException("User " + name + " bereits vorhanden");
	}
	

}
