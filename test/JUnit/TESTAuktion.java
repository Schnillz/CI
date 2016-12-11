package JUnit;

import java.math.BigDecimal;

import org.junit.*;

import mKIebay_team.Artikel;
import mKIebay_team.Auktion;
import mKIebay_team.Benutzer;
import mKIebay_team.MKIebay;
import mKIebay_team.Status;
import mKIebay_team.MKIebay.Superuser;
import static org.junit.Assert.*;


public class TESTAuktion{
	MKIebay projekt;
	Benutzer A,B,C,D;
	Auktion aa;
	
	@Before
	public void vorMethode() {
		projekt = new MKIebay("frank","super");
		MKIebay.Superuser superuser = (Superuser) projekt.login("frank","super");
		
		superuser.erstellen("hans","xas");
		superuser.erstellen("ilko","xas");
		superuser.erstellen("David","xas");
		superuser.erstellen("otto", "xas");
		
		A = projekt.login("hans","xas");
		B = projekt.login("ilko","xas");
		C = projekt.login("David", "xas");
		D = projekt.login("otto", "xas");
		aa = new Auktion(A,new Artikel("Baum"),new BigDecimal("2.31"), 3, projekt.getAuktionsVerwaltung());
		
	}
	
	@Test
	public void abbrechen(){
		aa.abbrechen();
		assertTrue(aa.getStatus().equals(Status.abgebrochen));
	}
	@Test
	public void aktiv(){
		assertTrue(aa.getStatus().equals(Status.aktiv));
	}
	@Test
	public void erfolgreichBeendet(){
		aa.biete(B, new BigDecimal("2.32"));
		aa.biete(C, new BigDecimal("3222.12"));
		aa.biete(D, new BigDecimal("3222.12"));

		assertTrue(aa.getStatus().equals(Status.beendet));
	}
}
