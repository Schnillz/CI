package mKIebay_team;

import java.io.Serializable;

/**
 * Erstellt Artikelobjekte
 * 
 * @author b4 (DB, IH, MS, SS)
 *
 */
public class Artikel implements Serializable {
	//erzeuge Seriennummer fuer das Objekt
	private static final long serialVersionUID = 1L;
	private static int counter = 0;
	private int id;
	private String name;
	
	/**
	 * Erstellt Artikel (Konstruktor 1)
	 *
	 * @param name Name des Artikels
	 */
	public Artikel(String name) throws RuntimeException {
		this.id = incCounter();
		this.setName(name);	
	}	
	
	/**
	 * Generiert Artikel-ID
	 */
	private static int incCounter() {
		return ++Artikel.counter;
	}
	
	/**
	 * Gibt ID zurück
	 * 
	 * @return ID des Artikels
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Gibt Name zurück
	 * 
	 * @return Name des Artikels
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setzt Namen
	 * 
	 * @param name Name des Artikels
	 */
	public void setName(String name) throws RuntimeException {
		if (name != null && name.length() > 2){
			this.name = name;
		} else throw new RuntimeException("Name ungueltig");
	}
	
	/**
	 * Gibt Artikel-Daten als String aus
	 * 
	 * @return String mit Name, ID
	 */
	@Override
	public String toString(){
		return this.getName();
	}
	
	/**
	 * Prüft zwei Artikel auf Identität
	 * 
	 * @param o Objekt das mit Artikel verglichen werden soll
	 * @return gibt boolean zurück, ob Artikel identisch sind
	 */
	@Override
	public boolean equals(Object o){
		
		if (o instanceof Artikel) {
			Artikel b = (Artikel) o;
			return this.getId() == b.getId();
		}
		return false;
	}
	
}
