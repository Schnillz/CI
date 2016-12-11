package GUI_Table;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

import GUI.GUI;

import mKIebay_team.Auktion;
import mKIebay_team.Benutzer;
import mKIebay_team.MKIebay;
import mKIebay_team.Status;

public class mkiTableModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 1L;
	private String[] columnNames = { "ID", "Artikel", "Anbieter", "Anzahl Gebote", "Status", "" };
	private Object[][] data;
	private Status[] rowStatus;
	private String[] auktionData;
	private	int[] auktionenID;
	private boolean eigene = false;
	private Benutzer benutzer;
	private MKIebay ebay;

	
	public mkiTableModel(ArrayList<Auktion> auktionen, Benutzer b, MKIebay ebay, boolean eigene){
		super();
		this.setBenutzer(b);
		this.setEbay(ebay);
		this.eigene = eigene;
		fillData(auktionen);
	}
	
	public int getAuktionsID(int zeile){
		return auktionenID[zeile];
	}
	
	private void setBenutzer(Benutzer b){
		if(b != null) this.benutzer = b;
		else GUI.meldung("GUI Fehler", 2);
	}
	
	private void setEbay(MKIebay ebay){
		if(ebay != null) this.ebay = ebay;
		else GUI.meldung("GUI Fehler", 2);
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int row, int column) {
		return data[row][column];
	}
	
	@Override
	public void setValueAt(Object gebot, int row, int column){
		data[row][column] = gebot;
	}
	
	

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int column) {
		return getValueAt(0, column).getClass();
	}
	
	
	private void fillData(ArrayList<Auktion> auktionen) {
		int letzteSpalte = getColumnCount();
		int size = auktionen.size();
		data = new Object[size][letzteSpalte];
		auktionenID = new int[auktionen.size()];

		rowStatus = new Status[size];
		// Array mit Daten befüllen

		for (int zeile = 0; zeile < auktionen.size(); zeile++) {

			Auktion tempAuktion = auktionen.get(zeile);
			rowStatus[zeile] = tempAuktion.getStatus();
			auktionData = this.getDataAuktion(tempAuktion);
			auktionenID[zeile] = tempAuktion.getId();

			if (eigene == true) {

				JButton abbrechenButton = new JButton("Abbrechen");
				for (int spalte = 0; spalte < letzteSpalte; spalte++) {

					//Tabelle mit Auktionsdaten befüllen
					if (spalte < letzteSpalte - 3)
						data[zeile][spalte] = auktionData[spalte];
					// Anzahl Gebote anzeigen wenn Auktion von Benutzer
					else if (spalte == letzteSpalte - 3 && tempAuktion.getAnbieter() == benutzer)
						data[zeile][spalte] = auktionData[spalte];
					// Anzahl Gebote anzeigen wenn Auktion nicht von Benutzer
					else if (spalte == letzteSpalte - 3 && tempAuktion.getAnbieter() != benutzer)
						data[zeile][spalte] = "X";
					// Kaufpreis anzeigen wenn Status = beendet
					else if (spalte == letzteSpalte - 2 && rowStatus[zeile].equals(Status.beendet))
						data[zeile][spalte] = "Kaufpreis " + tempAuktion.getHoechstesGebot() +  " (beendet)";
					// Gebot anzeigen wenn Benutzer geboten hat
					else if (spalte == letzteSpalte - 2 && rowStatus[zeile].equals(Status.aktiv) && tempAuktion.hatGeboten(benutzer))
						data[zeile][spalte] = "Ihr Gebot: " + tempAuktion.getGebot(benutzer) + " (aktiv)";
					// Status (aktiv) in aktive Auktionen schreiben
					else if (spalte == letzteSpalte - 2 && rowStatus[zeile].equals(Status.abgebrochen))
						data[zeile][spalte] = "(abgebrochen)";
					
					// Status (aktiv) in aktive Auktionen schreiben
					else if (spalte == letzteSpalte - 2 && rowStatus[zeile].equals(Status.aktiv))
						data[zeile][spalte] = "(aktiv)";
					// AbbrechenButton in aktive eigene Auktionen setzen
					else if(rowStatus[zeile].equals(Status.aktiv) && tempAuktion.getAnbieter() == benutzer) {
						data[zeile][spalte] = abbrechenButton;
					// Ansonsten inaktiven AbbrechenButton einsetzen
					}else{
						abbrechenButton.setEnabled(false);
						data[zeile][spalte] = abbrechenButton;
					}
				}
			} else {
				
				JButton bietenButton = new JButton("Bieten");
				JButton inaktivButton = new JButton("Inaktiv");
				inaktivButton.setEnabled(false);
				
				for (int spalte = 0; spalte < letzteSpalte; spalte++) {
					
					//Tabelle mit Auktionsdaten befüllen
					if (spalte < letzteSpalte - 3)
						data[zeile][spalte] = auktionData[spalte];
					// Anzahl Gebote anzeigen wenn Auktion von Benutzer
					else if (spalte == letzteSpalte - 3 && tempAuktion.getAnbieter() == benutzer)
						data[zeile][spalte] = auktionData[spalte];
					// Anzahl Gebote anzeigen wenn Auktion nicht von Benutzer
					else if (spalte == letzteSpalte - 3 && tempAuktion.getAnbieter() != benutzer)
						data[zeile][spalte] = "X";
					// Status (aktiv) in aktive Auktionen schreiben
					else if (spalte == letzteSpalte - 2 && rowStatus[zeile].equals(Status.aktiv) && tempAuktion.getAnbieter() == benutzer)
						data[zeile][spalte] = "(aktiv)";
					// Kaufpreis anzeigen wenn Status = beendet
					else if (spalte == letzteSpalte - 2 && rowStatus[zeile].equals(Status.beendet))
						data[zeile][spalte] = "Kaufpreis " + tempAuktion.getHoechstesGebot() +  " (beendet)";
					// Gebot anzeigen wenn Benutzer geboten hat
					else if (spalte == letzteSpalte - 2 && rowStatus[zeile].equals(Status.aktiv) && tempAuktion.hatGeboten(benutzer))
						data[zeile][spalte] = "Ihr Gebot: " + tempAuktion.getGebot(benutzer) + " (aktiv)";
					// Wenn Auktion aktiv und geboten werden kann schreibe nichts ins Textfeld
					else if (spalte == letzteSpalte - 2 && rowStatus[zeile].equals(Status.aktiv))
						data[zeile][spalte] = "";
					// Wenn Status = abgebrochen schreiben (abgebrochen)
					else if (spalte == letzteSpalte - 2 && rowStatus[zeile].equals(Status.abgebrochen))
						data[zeile][spalte] = "(abgebrochen)";
					// Inaktiven Button wenn eigene Auktion oder User bereits geboten hat
					else if (tempAuktion.getAnbieter() == benutzer || tempAuktion.hatGeboten(benutzer) || rowStatus[zeile].equals(Status.abgebrochen))
						data[zeile][spalte] = inaktivButton;
					else if (ebay.getSuperuser().equals(benutzer)){
						data[zeile][spalte] = inaktivButton;
					}
					// Button einsetzen
					else{
						data[zeile][spalte] = bietenButton;
					}
				}
			}

		}
	}
	
	
	//	Daten für Tabelle auslesen und vorbereiten
	private String[] getDataAuktion(Auktion a){
		String[] data = new String[columnNames.length];
		data[0] = String.valueOf(a.getId());
		data[1] = a.getArtikel().toString();
		data[2] = a.getAnbieter().toString();
		data[3] = String.valueOf(a.getAnz_gebote());
		return data;
	}
	
}
	

