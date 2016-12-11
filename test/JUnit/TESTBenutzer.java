package JUnit;

import static org.junit.Assert.*;

import mKIebay_team.Benutzer;
import mKIebay_team.MKIebay;
import mKIebay_team.MKIebay.Superuser;

import org.junit.Before;
import org.junit.Test;

public class TESTBenutzer {
	
	Benutzer a, b, c;
	MKIebay verwaltung;
	MKIebay.Superuser superuser;

	@Before
	public void vorMethode() {
		verwaltung = new MKIebay("super", "super");
		superuser = (Superuser) verwaltung.login("super","super");
		superuser.erstellen("David", "122");
		a = verwaltung.login("David", "122");
	}
	@Test(expected=Exception.class)
	public void ZweiIds() {
		superuser.erstellen("David", "122");
	}
	@Test
	public void BenutzerEquals() {
		b = new Benutzer("David", "122", verwaltung);
		assertTrue(a.equals(b));
	}
	
	// True kommt zurueck da benutzer eingeloggt
	@Test
	public void login() {
		a = verwaltung.login("David", "122");
		assertTrue(a.getLog());
	}
	
	@Test
	public void benutzerErstellen(){
		superuser.erstellen("Tom", "pass");
		c = verwaltung.login("Tom", "pass");
		assertNotNull(c.getLog());
	}
	
	@Test
	public void logout() {
		a.logout();
		assertFalse(a.getLog());
	}
	@Test
	public void suche(){
		assertNotNull(verwaltung.suche("David"));
	}
	
	@Test
	public void superuser(){
		assertTrue(superuser.getLog());
	}
	@Test
	public void superuserlogout(){
		superuser.logout();
		assertFalse( superuser.getLog());
		
	}
	
	@Test
	public void superusersuche(){
		assertNull(verwaltung.suche("super"));
	}



}
