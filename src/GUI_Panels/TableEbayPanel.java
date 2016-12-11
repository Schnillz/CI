package GUI_Panels;

import java.util.ArrayList;

import javax.swing.JScrollPane;

import Controller.Controller;
import GUI_Table.mkiTableEbay;

import mKIebay_team.Auktion;
import mKIebay_team.Benutzer;
import mKIebay_team.MKIebay;

public class TableEbayPanel extends JScrollPane {

	private static final long serialVersionUID = 1L;
	
	public TableEbayPanel(ArrayList<Auktion> arrayList, Benutzer b, Controller controller, MKIebay ebay, boolean eigene){
		super(new mkiTableEbay(b.auktionen(), b, controller, ebay, eigene));
	}

}
