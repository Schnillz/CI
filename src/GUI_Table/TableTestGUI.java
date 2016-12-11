package GUI_Table;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mKIebay_team.Artikel;
import mKIebay_team.Auktion;
import mKIebay_team.AuktionsVerwaltung;
import mKIebay_team.Benutzer;
import mKIebay_team.MKIebay;
import mKIebay_team.Status;


public class TableTestGUI {
	
	private JFrame window;
	private JTable table;
	private JScrollPane scrollPane;
	private ArrayList<Auktion> auktionen;
	Object[][] data;
	Status[] rowStatus;
	String[] auktionData;
	MKIebay mkiEbay;
	Benutzer benutzer;

	 
	//Testmethode zum auffüllen der Testliste
	public void fillList(){
		mkiEbay = new MKIebay("frank","super");
		AuktionsVerwaltung aVerwaltung = mkiEbay.getAuktionsVerwaltung();
		benutzer = new  Benutzer("Hans", "dasdasd", mkiEbay);
		Benutzer c = new  Benutzer("Wurst", "dasdasd", mkiEbay);
		Artikel baum = new Artikel("Baum");
		Artikel strauch = new Artikel("Strauch");
		aVerwaltung.neueAuktion(benutzer, baum, new BigDecimal("102.31"), 3);
		aVerwaltung.neueAuktion(c, strauch, new BigDecimal("102.31"), 3);
		aVerwaltung.getAuktion(1).setStatus(Status.beendet);
//		aVerwaltung.
//		a1.setStatus(Status.beendet);
		auktionen = aVerwaltung.getAuktionen(benutzer, mkiEbay.getSuperuser());
//		auktionen.add(a1); 
//		auktionen.add(a2);
	}
	
	public TableTestGUI(){
		fillList();
		window = new JFrame();
		//start table
		createTable(auktionen);
		window.getContentPane().add(scrollPane);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setSize(400, 300);
//		window.setLocationRelativeTo(null);
//		window.setLocationByPlatform(true);
//		window.setUndecorated(false);

//		System.out.println(window.getLocation());
//		window.setOpacity(Float.valueOf(0.5F)); 
		window.setVisible(true);

//		window.setResizable(false);
	}
	
//	------------------- Hilfsmethoden Start -------------------------- //
	
	
	private void createTable(ArrayList<Auktion> auktionen){
		
//		table = new mkiTable(auktionen, benutzer, mkiEbay);
	    	
		//	Tabelle konfigurieren
		table.setFillsViewportHeight(true);
		table.setRowHeight(30);
		
		scrollPane = new JScrollPane(table);
		
	}
	
	
}
