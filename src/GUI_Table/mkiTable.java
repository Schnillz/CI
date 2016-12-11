package GUI_Table;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import Controller.Controller;
import GUI.GUI;

import mKIebay_team.Auktion;
import mKIebay_team.Benutzer;
import mKIebay_team.MKIebay;
import mKIebay_team.Status;

public class mkiTable extends JTable {

	private static final long serialVersionUID = 1L;
	private Status[] rowStatus;
	private final int letzteSpalte = 6;
	private int sizeAuktionen;
	private TableCellRenderer defaultRenderer;
	private mkiTableButtonCE buttonCE;
	private mkiTableTextfieldCE textfieldCE;
	private ArrayList<mkiTableButtonCE> buttonCEList;
	private ArrayList<mkiTableTextfieldCE> textfieldCEList;
	private Controller controller;
	private boolean eigene = false;
	private MKIebay ebay;
	private Benutzer benutzer;
	private mkiTableModel tableModel;

	public mkiTable(ArrayList<Auktion> auktionen, Benutzer benutzer, Controller controller, MKIebay ebay, boolean eigene) {
		super(new mkiTableModel(auktionen, benutzer, ebay, eigene));
		this.setController(controller);
		this.setBenutzer(benutzer);
		this.setEbay(ebay);
		this.eigene = eigene;
		this.setTableModel();
		prepareComponents(auktionen, benutzer, this.eigene);
	}

	public void setController(Controller controller2) {
		this.controller = controller2;

	}

	private void setBenutzer(Benutzer benutzer) {
		if (benutzer != null) this.benutzer = benutzer;
		else GUI.meldung("GUI Fehler", 2);
	}
	
	public Benutzer getBenutzer(){
		return benutzer;
	}
	
	private void setEbay(MKIebay ebay){
		if(ebay != null) this.ebay = ebay;
		else GUI.meldung("GUI Fehler", 2);
	}
	
	public MKIebay getEbay(){
		return ebay;
	}

	private void setTableModel(){
		if (this.getModel() != null) this.tableModel = (mkiTableModel) this.getModel();
		else GUI.meldung("GUI Fehler", 2);
	}
	
	public mkiTableModel getTableModel(){
		return tableModel;
	}
	
	public int getLetzteSpalte() {
		return letzteSpalte;
	}

	public Status[] getRowStatus() {
		return rowStatus;
	}
	
	public ArrayList<mkiTableButtonCE> getButtonCEList() {
		return buttonCEList;
	}

	public ArrayList<mkiTableTextfieldCE> getTextfieldCEList() {
		return textfieldCEList;
	}

	// Nur Zellen in vorletzter Spalte sind beschreibbar falls Status=aktiv
	@Override
	public boolean isCellEditable(int row, int column) {
		boolean editable = false;

		if ((column == letzteSpalte - 1) && rowStatus[row].equals(Status.aktiv) && ebay.getAuktion(tableModel.getAuktionsID(row)).getAnbieter() == benutzer)
			editable = true;

		return editable;
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {

		Component c = super.prepareRenderer(renderer, row, column);
		JComponent jc = (JComponent) c;

		// Tabelle stylen
		if (!isRowSelected(row))
			jc.setBackground(Color.decode("#cccccc"));
		
		// Spalte gelb einfärben falls Benutzer geboten
		if (column < letzteSpalte - 1 && !isRowSelected(row) && rowStatus != null && rowStatus[row].equals(Status.aktiv)
				&& ebay.getAuktion(tableModel.getAuktionsID(row)).hatGeboten(benutzer)){
				jc.setBackground(Color.decode("#E8E574"));
			}
		
		// Spalte grün einfärben falls Status = aktiv
		else if (column < letzteSpalte - 1 && !isRowSelected(row) && rowStatus != null && rowStatus[row].equals(Status.aktiv)) {
			jc.setBackground(Color.decode("#9CD397"));
		}
		
		// Spalte rot einfärben falls Status = beendet
		else if (column < letzteSpalte - 1 && !isRowSelected(row) && rowStatus != null && rowStatus[row].equals(Status.beendet)) {
			jc.setBackground(Color.decode("#e2a8a8"));
		}
		
		return c;
	}

	@Override
	public TableCellEditor getCellEditor(int zeile, int spalte) {

		if (letzteSpalte - 1 == convertColumnIndexToModel(spalte)
				&& rowStatus[zeile].equals(Status.aktiv)) {
			return buttonCEList.get(zeile);
		} else {
			return super.getCellEditor(zeile, spalte);
		}

	}

	// Holt sich den Status aus der ArrayList und speichert diese extra ab
	private void getStatus(ArrayList<Auktion> auktionen) {
		rowStatus = new Status[auktionen.size()];
		int zaehler = 0;
		for (Auktion a : auktionen) {
			rowStatus[zaehler] = a.getStatus();
			zaehler++;
		}
	}

	// Hilfsmethode zur Vorbereitung der Komponenten der Tabelle aller Auktionen
	private void prepareComponents(ArrayList<Auktion> auktionen,Benutzer benutzer, boolean eigene) {

		this.getStatus(auktionen);
		this.setRowHeight(20);
		this.getTableHeader().setReorderingAllowed(false); 
		this.sizeAuktionen = auktionen.size();

		defaultRenderer = this.getDefaultRenderer(JButton.class);
		this.setDefaultRenderer(JButton.class, new mkiTableCellRenderer(defaultRenderer));

		buttonCEList = new ArrayList<mkiTableButtonCE>();
		textfieldCEList = new ArrayList<mkiTableTextfieldCE>();

		for (int i = 0; i < sizeAuktionen; i++) {

			if (eigene == false) {
				JButton jb = new JButton("Bieten");
				jb.addActionListener(controller.new TableActionListenerBieten(
						i, this));
				buttonCE = new mkiTableButtonCE(jb);
				buttonCEList.add(buttonCE);
				textfieldCE = new mkiTableTextfieldCE();
				textfieldCEList.add(textfieldCE);
			}
			if (eigene == true) {
				JButton jb = new JButton("Abbrechen");
				jb.addActionListener(controller.new TableActionListenerAbbrechen(
						i, this));
				buttonCE = new mkiTableButtonCE(jb);
				buttonCEList.add(buttonCE);
			}
		}
	}
}
