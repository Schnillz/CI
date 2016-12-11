package GUI_Table;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.border.BevelBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import mKIebay_team.Auktion;
import mKIebay_team.Benutzer;
import mKIebay_team.MKIebay;
import mKIebay_team.Status;
import Controller.Controller;

public class mkiTableEbay extends mkiTable{

	private static final long serialVersionUID = 1L;
	private int letzteSpalte = 6;

	public mkiTableEbay(ArrayList<Auktion> auktionen, Benutzer benutzer, Controller controller, MKIebay ebay, boolean eigene){
		super(auktionen, benutzer, controller, ebay, eigene);
	}
	
	@Override
	public boolean isCellEditable(int row, int column){
		boolean editable = false;
		
		if((column == letzteSpalte-2 || column == letzteSpalte-1) && 
				super.getRowStatus()[row].equals(Status.aktiv) && 
				getAuktion(row).getAnbieter() != super.getBenutzer()
				&& !getAuktion(row).hatGeboten(super.getBenutzer()) 
				&& !super.getEbay().getSuperuser().equals(super.getBenutzer()))
			editable = true;
		
		return editable;
	}
	
	@Override
	public TableCellEditor getCellEditor(int zeile, int spalte) {

		if (letzteSpalte - 2 == convertColumnIndexToModel(spalte)) {
			return super.getTextfieldCEList().get(zeile);
		}
		if (letzteSpalte - 1 == convertColumnIndexToModel(spalte)
				&& super.getRowStatus()[zeile].equals(Status.aktiv)
				&& getAuktion(zeile).getAnbieter() != super.getBenutzer()
				&& !super.getEbay().getSuperuser().equals(super.getBenutzer())) {
			return super.getButtonCEList().get(zeile);
		} else {
			return super.getCellEditor(zeile, spalte);
		}	
	}
	
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {

		Component c = super.prepareRenderer(renderer, row, column);
		JComponent jc = (JComponent) c;
		
		// Tabelle stylen
		if (!isRowSelected(row))
			jc.setBackground(Color.decode("#cccccc"));
		
		// Zeile gelb einfärben falls Benutzer schon geboten hat
		if (column < letzteSpalte - 1 && !isRowSelected(row) && super.getRowStatus() != null && super.getRowStatus()[row].equals(Status.aktiv)
				&& getAuktion(row).hatGeboten(super.getBenutzer())) {
			jc.setBackground(Color.decode("#E8E574"));
			
		// Zeile rot einfärben bei Status = beendet
		} else if (column < letzteSpalte - 1 && !isRowSelected(row) && super.getRowStatus() != null && super.getRowStatus()[row].equals(Status.beendet)) {
			jc.setBackground(Color.decode("#e2a8a8"));
					
		// Spalte grün einfärben bei Status = aktiv
		} else if (column < letzteSpalte - 2 && !isRowSelected(row) && super.getRowStatus() != null && super.getRowStatus()[row].equals(Status.aktiv)) {
			jc.setBackground(Color.decode("#9CD397"));
			
		// Spalte grün einfärben bei Status = aktiv und eigene Auktion
		} else if (column < letzteSpalte - 1 && !isRowSelected(row)
				&& super.getRowStatus() != null
				&& super.getRowStatus()[row].equals(Status.aktiv)
				&& getAuktion(row).getAnbieter() == super.getBenutzer()) {
			jc.setBackground(Color.decode("#9CD397"));

		// Bietenfeld stylen
		} else if (column == letzteSpalte - 2
				&& !isRowSelected(row)
				&& super.getRowStatus() != null
				&& super.getRowStatus()[row].equals(Status.aktiv)
				&& getAuktion(row).getAnbieter() != super.getBenutzer()
				&& !getAuktion(row).hatGeboten(super.getBenutzer())) {
			jc.setBackground(Color.WHITE);
			jc.setBorder(new BevelBorder(BevelBorder.LOWERED));
	
		}
		return c;
	}
		
		// Hilfsmethode um Auktion aus TableModel zu holen
		private Auktion getAuktion(int row){
			return super.getEbay().getAuktion(super.getTableModel().getAuktionsID(row));
		}
}
