package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JTable;

import mKIebay_team.Artikel;

import mKIebay_team.Auktion;
import mKIebay_team.Benutzer;
import mKIebay_team.MKIebay;
import mKIebay_team.MKIebay.Superuser;

import GUI.GUI;
import GUI_Panels.FooterAdmin;
import GUI_Panels.FooterAuktion;
import GUI_Table.mkiTable;
import GUI_Table.mkiTableModel;

public class Controller {
	private MKIebay ebay;
	private GUI gui;
	private Benutzer benutzer;
	private String comboListe = "Angebote";

	public Controller(MKIebay ebay, GUI gui) {
		if (ebay == null || gui == null) {
			throw new RuntimeException(
					"keine GUI oder kein Datenmodell ¸bergeben");
		} else {
			this.ebay = ebay;
			this.gui = gui;
		}
	}

	public String getComboListe() {
		return comboListe;
	}

	public ActionListener buttonLogin = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			Benutzer b = ebay.login(gui.getLogPanel().getName(), gui
					.getLogPanel().getPass());
			if (b != null) {
				benutzer = b;
				gui.setContentEigene(b, "Angebote");
			} else {
				GUI.meldung("login fehlgeschlagen",2);
			}
		}
	};

	public ActionListener buttonLogout = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (benutzer != null) {
				benutzer.logout();
				comboListe = "Angebote";
				gui.setContentLogin();
			} else {
				GUI.meldung("GUI Fehler",2);
			}
		}
	};

	public ActionListener buttonHome = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			gui.setContentEigene(benutzer, comboListe);
		}
	};

	public ActionListener buttonAuktionen = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			comboListe = "Angebote";
			gui.setContentEbay();
		}
	};

	public ActionListener comboAuktionenAL = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox) e.getSource();
			String listenTyp = (String) cb.getSelectedItem();
			comboListe = listenTyp;
			try{
			gui.setContentEigene(benutzer, listenTyp);
			}
			catch(Exception e1){
				GUI.meldung("Liste konnte nicht geladen werden",2);
				comboListe = "Angebote";
				gui.setContentEigene(benutzer, comboListe);
			}
		}
	};

	public ActionListener auktionErstellenAL = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				FooterAuktion panel = (FooterAuktion) gui.getFooterPanel();
				
				String aString = panel.getArtikelName();
				BigDecimal minG = new BigDecimal(panel.getVerkaufspreis());
				int anzG = Integer.parseInt(panel.getGebote());
					
					if (aString.length() > 30 || minG.compareTo(new BigDecimal("1000000000")) == 1 || anzG > 1000)
						throw new RuntimeException("Artikelname, mindestGebot oder Anzahl der Gebote zu groﬂ");
				
				benutzer.anbieten(new Artikel(aString), minG , anzG);

			} catch (Exception e1) {
				GUI.meldung("Fehlerhafte Eingabe",2);
			}
			gui.setContentEbay();
		}
	};
	public ActionListener buttonSpeichern = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (comboListe.equals("Geladen")){
				GUI.meldung("Geladene Liste kann nicht gespeichert werden", 2);
			}else{
			File file = GUI.getFile(true);
			ArrayList<Auktion> a = null;
			try {
				if (comboListe.equals("Angebote")) {
					a = benutzer.angebote();
				} else if (comboListe.equals("Gebote")) {
					a = benutzer.gebote();
				} else if (comboListe.equals("Verkauft")) {
					a = benutzer.verkauft();
				} else if (comboListe.equals("Gekauft")) {
					a = benutzer.gekauft();
				}
				benutzer.speichern(file, a);
				GUI.meldung("Daten wurden gespeichert", 1);
			} catch (Exception e1) {
				GUI.meldung("Speichern fehlgeschlagen", 2);
			}
			}
		}
	};

	public ActionListener buttonLaden = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				comboListe = "Geladen";
				gui.setContentEigene(benutzer ,comboListe);
				}
				catch(Exception e1){
					GUI.meldung("Liste konnte nicht geladen werden",2);
					comboListe = "Angebote";
					gui.setContentEigene(benutzer, comboListe);
				}
		}
	};

	public ActionListener buttonErstellen = new ActionListener() {
		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				if (benutzer.equals(ebay.getSuperuser())) {
					Superuser s = (Superuser) benutzer;
					FooterAdmin panel = (FooterAdmin) gui.getFooterPanel();
					String name = panel.getTfName().getText();
					String pass = panel.getPfPass().getText();
					
					if (name.length() > 15 || pass.length() > 20)
						throw new RuntimeException("name oder PW zu lang");
					s.erstellen(name,pass);				
					GUI.meldung("Benutzer wurde erfolgreich angelegt", 1);
				}
			} catch (Exception e1) {
				GUI.meldung("Benutzer konnte nicht erstellt werden", 2);
			}
			gui.setContentEbay();
		}
	};

	public class TableActionListenerBieten implements ActionListener {

		private int id;
		private int spalte;
		private int zeile;
		private mkiTableModel tableModel;

		public TableActionListenerBieten(int zeile, mkiTable table) {
			this.setTableModel(table);
			this.setId(zeile);
			this.setSpalte(table);
			this.setZeile(zeile);

		}

		private void setTableModel(JTable table) {
			mkiTableModel tableModel = (mkiTableModel) table.getModel();
			this.tableModel = tableModel;
		}

		private void setId(int zeile) {
			this.id = tableModel.getAuktionsID(zeile);
		}

		private void setZeile(int zeile) {
			this.zeile = zeile;
		}

		private void setSpalte(mkiTable table) {
			this.spalte = table.getColumnCount();
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			try {

				BigDecimal gebot = new BigDecimal(
						(String) tableModel.getValueAt(zeile, spalte - 2));
				if (gebot.compareTo(new BigDecimal("1000000000")) == 1) {
					throw new RuntimeException("Gebot zu groﬂ");
				} else {
					benutzer.bieten(id, gebot);
					GUI.meldung(  "Erfolgreich auf Auktion geboten", 1);
				}

			} catch (Exception ee) {
				GUI.meldung("Bieten fehlgeschlagen!", 2);
			}
			gui.setContentEbay();
		}
	}

	public class TableActionListenerAbbrechen implements ActionListener {

		private int id;
		private mkiTableModel tableModel;

		public TableActionListenerAbbrechen(int zeile, mkiTable table) {
			this.setTableModel(table);
			this.setId(zeile);
		}

		private void setTableModel(JTable table) {
			mkiTableModel tableModel = (mkiTableModel) table.getModel();
			this.tableModel = tableModel;
		}

		private void setId(int zeile) {
			this.id = tableModel.getAuktionsID(zeile);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				benutzer.abbrechen(id);
				GUI.meldung("Auktion erfolgreich abgebrochen", 1);
				gui.setContentEigene(benutzer, comboListe);

			} catch (Exception e2) {
				GUI.meldung("Auf diese Auktion wurde bereits geboten!", 2);
			}
		}
	}
}
