package GUI_Panels;

import java.util.ArrayList;

import javax.swing.JScrollPane;

import Controller.Controller;
import GUI_Table.mkiTable;

import mKIebay_team.Auktion;
import mKIebay_team.Benutzer;
import mKIebay_team.MKIebay;

public class TableEigenePanel extends JScrollPane {

	private static final long serialVersionUID = 1L;
	
	public TableEigenePanel(ArrayList<Auktion> arrayList, Benutzer b, Controller controller, MKIebay ebay){
		super(new mkiTable(arrayList, b, controller, ebay, true));
	}
	

}
