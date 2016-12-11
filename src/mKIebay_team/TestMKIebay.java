package mKIebay_team;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import mKIebay_team.MKIebay.Superuser;

/**
 * Testklasse zu MKIebay
 * 
 * @author b4 (DB, IH, MS, SS)
 *
 */
public class TestMKIebay {

	/**
	 * Testet MKIebay auf Bieten, Suchen
	 * 
	 * @param args
	 * @throws IOException Wirft Input-/ oder Output-Fehlermeldung
	 * 
	 */
	public static void main(String[] args) throws IOException{
		
		MKIebay projekt = new MKIebay("frank","super");
		MKIebay.Superuser superuser = (Superuser) projekt.login("frank","super");
		
		superuser.erstellen("hans","xas");
		projekt.login("hans","xas");
		Benutzer hans = projekt.suche("hans");	
		
		superuser.erstellen("penny", "markt");
		Benutzer penny = projekt.login("penny", "markt");
		
		superuser.erstellen("ilko", "milko");
		Benutzer ilko = projekt.login("ilko", "milko");
		
		try{
			superuser.erstellen("penny", "neu");
		}
		catch (Exception e){
			System.out.println("user bereits vorhanden");
		}
		
		hans.anbieten(new Artikel("Haus"), new BigDecimal("1.12"), 2);
		penny.bieten(1, new BigDecimal("1.12"));
		penny.gebote();	
		
		penny.auktionen();
		
		ilko.bieten(1,new BigDecimal("1.5"));
		
		hans.speichern(new File("hans.csv"), hans.verkauft());
		hans.laden(new File("hans.csv"));
		
		ilko.speichern(new File("ilko.ser"), ilko.gekauft());
		ilko.laden(new File("ilko.ser"));
		
		System.out.println(projekt.suche("hans"));		
		System.out.println(penny.suche("hans"));
		
		hans.logout();		
		hans.verkauft();
					
		projekt.login("hans","xas");
		hans.verkauft();
	}

}
