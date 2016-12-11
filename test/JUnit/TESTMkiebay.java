package JUnit;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import mKIebay_team.Benutzer;
import mKIebay_team.MKIebay;

import org.junit.Before;
import org.junit.Test;


public class TESTMkiebay {
	
	Benutzer b;
	MKIebay.Superuser superuser;
	MKIebay verwaltung;
	
	
	@Before
	public void vorMethode(){
		verwaltung = new MKIebay("super", "user");
		superuser = verwaltung.getSuperuser();
		superuser.login();
		superuser.erstellen("David", "pass");
	}

	@Test
	public void NeuerBenutzer() {
		b = new Benutzer("David", "laisy", verwaltung);
		assertNotNull(b);
	}
	
	@Test
	public void BenutzerLogin() {
		assertNotNull(verwaltung.login("David", "pass"));
	}

	@Test
	public void BenutzerSuche() {
		assertNotNull(verwaltung.suche("David"));
	}
	
	@Test(expected=Exception.class)
	public void BietenSuperuser() {
		superuser.bieten(2, new BigDecimal("3"));
	}
	
	@Test
	public void NeueAuktion() {
	
		assertNotNull(verwaltung.getAuktionen(verwaltung.getSuperuser()));
	}
	
	@Test
	public void NeueVerwaltung(){
		verwaltung = new MKIebay("super", "user");
		assertNotNull(verwaltung);
	}
	
}
