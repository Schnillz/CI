package JUnit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import mKIebay_team.Artikel;


public class TESTArtikel {
	
	Artikel a,b,c;
	
	@Before
	public void vorMethode(){
		a= new Artikel("testa");
		b= new Artikel("testb");
	}
	
	@Test
	public void NeuerArtikel(){
		assertNotNull(a);
	}

	@Test (expected=Exception.class)
	public void NameNull(){
		a = new Artikel("");
		assertNull(a);
	}
	
	@Test
	public void ArtikelGleich(){
		assertFalse(a.equals(b));
	}

}
