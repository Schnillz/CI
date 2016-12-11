package GUI;

import java.math.BigDecimal;

import mKIebay_team.Artikel;
import mKIebay_team.Benutzer;
import mKIebay_team.MKIebay;
import mKIebay_team.MKIebay.Superuser;

public class StartGUI {
	public static void main(String[] args) {
		MKIebay ebay = new MKIebay("frank","super");
		MKIebay.Superuser superuser = (Superuser) ebay.login("frank","super");		
		
		superuser.erstellen("David", "1234");
		superuser.erstellen("Manu", "1234");
		superuser.erstellen("Basti", "1234");
		superuser.erstellen("Ilko", "1234");
		
		Benutzer david = ebay.login("David", "1234");
		Benutzer manu = ebay.login("Manu", "1234");
		Benutzer basti = ebay.login("Basti", "1234");
		Benutzer ilko = ebay.login("Ilko", "1234");
		
		david.anbieten(new Artikel("Hund1"), new BigDecimal("2"), 3);
		david.anbieten(new Artikel("Hund2"), new BigDecimal("1"), 3);
		david.anbieten(new Artikel("Hund3"), new BigDecimal("1"), 3);
		david.anbieten(new Artikel("Hund4"), new BigDecimal("1"), 3);
		david.anbieten(new Artikel("Hund5"), new BigDecimal("1"), 3);
		
		ilko.anbieten(new Artikel("Bier6"), new BigDecimal("1"), 3);
		manu.anbieten(new Artikel("Bier7"), new BigDecimal("1"), 3);
		basti.anbieten(new Artikel("Bier8"), new BigDecimal("1"), 3);
		
		ilko.bieten(1, new BigDecimal("1.0"));
		manu.bieten(1, new BigDecimal("1.0"));
		basti.bieten(1, new BigDecimal("1.0"));

		manu.bieten(2, new BigDecimal("2.0"));
		ilko.bieten(2, new BigDecimal("3.0"));
		basti.bieten(2, new BigDecimal("1.0"));
		
		david.abbrechen(3);
		
		manu.bieten(4, new BigDecimal("1.0"));
		basti.bieten(4, new BigDecimal("1.0"));
		
		ilko.bieten(5, new BigDecimal("1.0"));
		manu.bieten(5, new BigDecimal("1.0"));
		basti.bieten(5, new BigDecimal("1.0"));
		
		david.bieten(6, new BigDecimal("2.0"));
		manu.bieten(6, new BigDecimal("1.0"));
		basti.bieten(6, new BigDecimal("1.0"));
		
		basti.logout();
		ilko.logout();
		david.logout();
		manu.logout();
		
		
		new GUI(ebay);
	}
}
