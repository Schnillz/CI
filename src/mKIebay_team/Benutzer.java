package mKIebay_team;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Erstellt Benutzerobjekte
 * 
 * @author b4 (DB, IH, MS, SS)
 * 
 */
public class Benutzer implements Serializable {

	// erzeuge Seriennummer fuer das Objekt
	private static final long serialVersionUID = 1L;

	private String name;
	private String pass;
	private boolean eingeloggt = false;
	protected MKIebay verwaltung;

	/**
	 * Erstellt Benutzer (Konstruktor)
	 * 
	 * @param name
	 *            Name des Benutzers
	 * @param pass
	 *            Passwort des Benutzers
	 * @param verwaltung
	 *            Das dem Benutzer zugeordnete MKIebay
	 */
	public Benutzer(String name, String pass, MKIebay verwaltung)
			throws RuntimeException {
		this.setName(name);
		this.setPass(pass);
		this.setVerwaltung(verwaltung);
	}

	/**
	 * Setzt Passwort
	 * 
	 * @param pass
	 *            Passwort des Benutzers
	 */
	private void setPass(String pass) throws RuntimeException {
		if (pass == null || pass.length() < 3) {
			throw new RuntimeException("Passwort fehlerhaft");
		}
		this.pass = pass;
	}

	/**
	 * Gibt Passwort zurueck
	 * 
	 * @return Passwort des Benutzers
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * Setzt Name
	 * 
	 * @param name
	 *            Name des Benutzers
	 */
	private void setName(String name) throws RuntimeException {
		if (name == null || name.length() < 2) {
			throw new RuntimeException("Name fehlerhaft");
		}
		this.name = name;
	}

	/**
	 * Gibt Name zurueck
	 * 
	 * @return Name des Benutzers
	 */
	public String getName() {
		return name;
	}

	/**
	 * setzt die Benutzerverwaltung
	 * 
	 * @param verwaltung
	 *            Benutzerverwaltung
	 */

	public void setVerwaltung(MKIebay verwaltung) throws RuntimeException {
		if (verwaltung == null) {
			throw new RuntimeException("Verwaltung null");
		} else {
			this.verwaltung = verwaltung;
		}
	}

	/**
	 * Prüft zwei Benutzer auf Identitaet
	 * 
	 * @param o
	 *            Object, das mit Benutzer verglichen werden soll
	 * @return boolean o identisch mit Benutzer?
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Benutzer) {
			Benutzer b = (Benutzer) o;
			return b.getName().equals(this.getName());
		}
		return false;
	}

	/**
	 * Gibt Namen des Benutzers als String zurueck
	 * 
	 * @return Name als String
	 */
	@Override
	public String toString() {
		return this.getName();
	}

	/**
	 * Auf Artikel in einer Auktion bieten
	 * 
	 * @param id
	 *            ID der Auktion
	 * @param gebot
	 *            Höhe des Gebots
	 */
	public void bieten(int id, BigDecimal gebot) throws RuntimeException {
		if (eingeloggt) {
			try {
				verwaltung.biete(this, verwaltung.getAuktion(id), gebot);
			} catch (RuntimeException e) {
				throw new RuntimeException("fehler beim bieten");
			}
		} else
			 throw new RuntimeException("nicht eingeloggt!");
	}

	/**
	 * Artikel in neuer Auktion anbieten
	 * 
	 * @param a
	 *            Artikel, der angeboten wird
	 * @param mind
	 *            Gefordertes MindestGebot
	 * @param gebote
	 *            Anzahl der geforderten Gebote, bis Auktion beendet wird
	 */
	public void anbieten(Artikel a, BigDecimal mind, int gebote)
			throws RuntimeException {
		if (eingeloggt) {
			try {
				verwaltung.neueAuktion(this, a, mind, gebote);
			} catch (RuntimeException e) {
				throw new RuntimeException("fehler beim anbieten");
			}
		} else
			 throw new RuntimeException("nicht eingeloggt!");
	}

	/**
	 * Bricht Auktion des Benutzers ab
	 * 
	 * @param id
	 *            ID der Auktion
	 */
	public void abbrechen(int id) throws RuntimeException {
		if (eingeloggt) {
//			try {
				verwaltung.abbrechen(this, verwaltung.getAuktion(id));
//			} catch (RuntimeException e) {
//				throw new RuntimeException("fehler beim abbrechen");
//			}
		} else
			 throw new RuntimeException("nicht eingeloggt!");
	}

	/**
	 * Methode gibt alle Auktionen aus
	 */

	public ArrayList<Auktion> auktionen() throws RuntimeException {
		if (eingeloggt) {
			ArrayList<Auktion> a;
			try {
				a = verwaltung.getAuktionen(this);
			} catch (RuntimeException e) {
				throw new RuntimeException("fehler bei der liste der auktionen");
			}
			return a;
		} else
			 throw new RuntimeException("nicht eingeloggt!");
	}

	/**
	 * Uebergibt der Methode AusgabeListe eine ArrayList aller gekauften Artikel
	 * (in Form von Auktionen)
	 * 
	 * @throws IOException
	 */
	public ArrayList<Auktion> gekauft() throws RuntimeException {
		if (eingeloggt) {
			ArrayList<Auktion> a;
			try {
				a = verwaltung.getGekauft(this);
			} catch (RuntimeException e) {
				throw new RuntimeException("fehler bei der liste der auktionen");
			}
			return a;
		} else
			 throw new RuntimeException("nicht eingeloggt!");
	}

	/**
	 * Uebergibt der Methode AusgabeListe ArrayList aller verkauften Artikel (in
	 * Form von Auktionen)
	 * 
	 * @throws IOException
	 */
	public ArrayList<Auktion> verkauft() throws RuntimeException {
		if (eingeloggt) {
			ArrayList<Auktion> a;
			try {
				a = verwaltung.getVerkauft(this);
			} catch (RuntimeException e) {
				throw new RuntimeException("fehler bei der liste der auktionen");
			}
			return a;
		} else
			 throw new RuntimeException("nicht eingeloggt!");
	}

	/**
	 * Uebergibt der Methode AusgabeListe ArrayList mit allen aktuellen
	 * Angeboten
	 */
	public ArrayList<Auktion> angebote() throws RuntimeException {
		if (eingeloggt) {
			ArrayList<Auktion> a;
			try {
				a = verwaltung.getAngebote(this);
			} catch (RuntimeException e) {
				throw new RuntimeException("fehler bei der liste der auktionen");
			}
			return a;
		} else
			 throw new RuntimeException("nicht eingeloggt!");
	}

	/**
	 * Uebergibt der Methode Ausgabeliste ArrayList mit allen aktuellen
	 * Auktionen, auf die der Benutzer geboten hat
	 */
	public ArrayList<Auktion> gebote() throws RuntimeException {
		if (eingeloggt) {
			ArrayList<Auktion> a;
			try {
				a = verwaltung.getGebote(this);
			} catch (RuntimeException e) {
				throw new RuntimeException("fehler bei der liste der auktionen");
			}
			return a;
		} else
			 throw new RuntimeException("nicht eingeloggt!");
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
		if (eingeloggt) {
			try {
				verwaltung.speichern(f, a);
			} catch (RuntimeException e) {
				throw new RuntimeException("fehler beim speichern");
			}
		} else
			 throw new RuntimeException("nicht eingeloggt!");
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
		if (eingeloggt) {
			ArrayList<Auktion> a;
			try {
				a = verwaltung.laden(f);
			} catch (RuntimeException e) {
				throw new RuntimeException("fehler bei der liste der auktionen");
			}
			return a;
		} else
			 throw new RuntimeException("nicht eingeloggt!");
	}

	/**
	 * Sucht Benutzer
	 * 
	 * @param name
	 *            Name des gesuchten Benutzers
	 * @return gesuchter Benutzer
	 */
	public Benutzer suche(String name) throws RuntimeException {
		if (eingeloggt && name != null) {
			Benutzer b;
			try {
				b = verwaltung.suche(name);
			} catch (RuntimeException e) {
				throw new RuntimeException("fehler beim suchen");
			}
			if (b == null) {
				throw new RuntimeException("user nicht geufunden");
			}
			return b;
		} else
			 throw new RuntimeException("nicht eingeloggt!");
	}

	/**
	 * Loggt Benutzer ein
	 */
	public void login() {
		this.eingeloggt = true;
	}

	/**
	 * Loggt Benutzer aus
	 */
	public void logout() throws RuntimeException {
		if (eingeloggt) {
			this.eingeloggt = false;
		} else
			 throw new RuntimeException("nicht eingeloggt!");
	}

	/**
	 * Loginstatusabfrage
	 * 
	 * @return Eingeloggt/nicht eingeloggt
	 */
	public boolean getLog() {
		return eingeloggt;
	}

	/**
	 * Gibt Auktionen auf der Konsole aus
	 * 
	 * @param aa
	 *            ArrayList von Auktionen
	 */
//	private void ausgabeListe(ArrayList<Auktion> aa) {
//		if (aa == null || aa.isEmpty()) {
//			System.out.println("keine Auktionen");
//		}
//		else {
//		for (Auktion a : aa) {
//			System.out.println(a);
//		}
//		}
//	}

}
