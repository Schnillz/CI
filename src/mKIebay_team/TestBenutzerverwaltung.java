package mKIebay_team;

import java.io.IOException;

import mKIebay_team.MKIebay.Superuser;

/**
 * Testklasse zur BenutzerVerwaltung (MKIebay)
 * 
 * @author b4 (DB, IH, MS, SS)
 *
 */
public class TestBenutzerverwaltung {
	
	/**
	 * Testet MKIebay auf Funktionalität Benutzer erstellen/suchen, login/logout
	 * 
	 * @param args
	 * @throws IOException Wirft Input-/ oder Output-Fehlermeldung
	 */
	public static void main(String[] args) throws IOException{
		
		MKIebay projekt = new MKIebay("frank","super");
		MKIebay.Superuser superuser = (Superuser) projekt.login("frank","super");
		
		
		//Benutzer erstelle und einloggen
		
		superuser.erstellen("hans","xas");
		projekt.login("hans","xas");
		Benutzer hans = projekt.suche("hans");	
		
		superuser.erstellen("penny", "markt");
		Benutzer penny = projekt.login("penny", "markt");
		
		try{
			superuser.erstellen("penny", "neu");
		}
		catch (Exception e){
			System.out.println("user bereits vorhanden");
		}	
		
		//Benutzer Suchen

		System.out.println(projekt.suche("hans"));		
		System.out.println(penny.suche("hans"));
		try{
			System.out.println(penny.suche("frank"));
		}
		catch(Exception e){
			System.out.println("User nicht gefunden");
		}		
		hans.logout();			
		projekt.login("hans","xas");
		superuser.logout();
		superuser = (Superuser) projekt.login("frank","super");
		System.out.println(projekt.suche("hans"));
	}

}
