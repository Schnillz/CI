package JUnit;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import mKIebay_team.Artikel;
import mKIebay_team.Auktion;
import mKIebay_team.AuktionsVerwaltung;
import mKIebay_team.Benutzer;
import mKIebay_team.MKIebay;
import mKIebay_team.MKIebay.Superuser;
import mKIebay_team.Status;


public class TESTAuktionsverwaltung {

	MKIebay ebay;
	Benutzer b, b2, b3, b4;
	AuktionsVerwaltung av;
	Auktion a;
	Artikel ar1;
	Artikel ar2;
	Artikel ar3;
	ArrayList<Auktion> arr;

	@Before
	public void vorMethode() {
		ebay = new MKIebay("frank", "super");
		Superuser su = (Superuser) ebay.login("frank", "super");
		su.erstellen("hanz", "1234");
		su.erstellen("jochen", "4321");
		su.erstellen("ich", "pass");
		su.erstellen("du", "pass");
		
		b = ebay.login("hanz","1234");
		b2 = ebay.login("jochen", "4321");
		
		av = ebay.getAuktionsVerwaltung();
		ar1 = new Artikel("stock");
		ar2 = new Artikel("biene");
		ar3 = new Artikel("vogel");
		av.neueAuktion(b, ar1, new BigDecimal("1.4"), 3);
		av.neueAuktion(b, ar2, new BigDecimal("1.4"), 3);
		av.neueAuktion(b, ar3, new BigDecimal("1.4"), 3);
	}
	
	@Test
	public void GetGebote(){
		a = av.getAuktion(4);
		b2.bieten(4, new BigDecimal("3.99"));
		assertTrue(av.getGebote(b2).contains(a));
	}
	
	@Test(expected=Exception.class)
	public void AbbrechenNachGebot(){
		a = av.getAuktion(1);
		b2.bieten(1, new BigDecimal("3.99"));
		b.abbrechen(1);
		assertTrue(a.getStatus().equals(Status.aktiv));
	}
	
	@Test(expected=Exception.class)
	public void DoppeltBieten(){
		a = av.getAuktion(1);
		b2.bieten(1, new BigDecimal("3.99"));
		b2.bieten(1, new BigDecimal("7.75"));
		assertTrue(a.getAnz_gebote()==1);

	}
	
}

