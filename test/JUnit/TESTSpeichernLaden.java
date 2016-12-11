package JUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import mKIebay_team.Artikel;
import mKIebay_team.Datenstrom_CSV;
import mKIebay_team.Datenstrom_SER;
import mKIebay_team.IDatenzugriff;


public class TESTSpeichernLaden {
	IDatenzugriff idaten, idaten2;
	ArrayList<Artikel> aSpeichern, aLaden;
	Properties pSpeichern, pLaden, propsl, pSpeichern2, pLaden2, propsl2;
	Artikel a1, a2, a3;
	File fCSV,fSER;
	String[] fields;
	
	@SuppressWarnings("unchecked")
	@Before
	public void vorMethode() throws IOException{
		idaten = new Datenstrom_SER();
		idaten2 = new Datenstrom_CSV();
		aSpeichern = new ArrayList<Artikel>();
		pSpeichern= new Properties();
		pLaden= new Properties();
		
		fCSV = new File("test.csv");
		fSER = new File("test.ser");
		
		/////
				
		pSpeichern = new Properties();
		pLaden = new Properties();
		propsl = new Properties();

		pSpeichern.put("file",fCSV);
		pSpeichern.put(1,"1;felix;funktioniert");
		pSpeichern.put(2,"2;max;funktioniert_auch");
		
		idaten.datenOutput(pSpeichern);

		propsl.put("file", fCSV);
		pLaden = idaten.datenInput(propsl);
		
		////
		
		pSpeichern2 = new Properties();
		pLaden2 = new Properties();
		propsl2 = new Properties();
		
		aSpeichern = new ArrayList<Artikel>();
		aLaden = new ArrayList<Artikel>();
		
		pSpeichern2.put("file", fSER);

		a1 = new Artikel("Baum");
		a2 = new Artikel("Blume");
		a3 = new Artikel("Auto");
		
		aSpeichern.add(a1);
		aSpeichern.add(a2);
		
		pSpeichern2.put("liste", aSpeichern);
		
		idaten.datenOutput(pSpeichern2);
		
		propsl2.put("file", fSER);
		
		pLaden2 = idaten.datenInput(propsl2);
		aLaden = (ArrayList<Artikel>)pLaden2.get("liste");
		
	}
	
	@Test
	public void LadenCSV(){
		assertNotNull(pLaden.get(1));
	}
	
	@Test
	public void LadenCSVFail(){
		assertNull(pLaden.get(12));
	}

	@Test
	public void LadenSER(){
		assertTrue(aLaden.contains(a1));
	}
	
	@Test
	public void LadenSERFail(){
		assertFalse(aLaden.contains(a3));
	}
	
}
