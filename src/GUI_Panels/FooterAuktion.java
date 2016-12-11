package GUI_Panels;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.Controller;

public class FooterAuktion extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JLabel labelNeueAuktion = new JLabel();
	private JLabel labelArtikelName = new JLabel();
	private JLabel labelVerkaufspreis = new JLabel();
	private JLabel labelGebote = new JLabel();
	private JTextField tfArtikel= new JTextField();
	private JTextField tfVerkaufspreis = new JTextField();
	private JTextField tfAnzGebote = new JTextField();
	private JButton btnErstellen = new JButton();
	private BoxLayout layout = new BoxLayout(this, BoxLayout.LINE_AXIS);
	
	public FooterAuktion(Controller controller){
		
		this.setLayout(layout);
		Dimension d1 = new Dimension(100, 20);
		Dimension d2 = new Dimension(40, 20);
		
		labelNeueAuktion.setText("Neue Auktion erstellen:");
		labelArtikelName.setText("Artikelname:");
		labelVerkaufspreis.setText("Mindestpreis:");
		labelGebote.setText("Anzahl Gebote:");
		
		btnErstellen.addActionListener(controller.auktionErstellenAL);
		btnErstellen.setText("OK");
		
		tfArtikel.setPreferredSize(d1);
		tfVerkaufspreis.setPreferredSize(d2);
		tfAnzGebote.setPreferredSize(d2);
		
		this.add(labelNeueAuktion);
		this.add(Box.createRigidArea(new Dimension(20,0)));
				
		this.add(labelArtikelName);
		this.add(tfArtikel);
		this.add(Box.createRigidArea(new Dimension(10,0)));
		this.add(labelVerkaufspreis);
		this.add(tfVerkaufspreis);
		this.add(Box.createRigidArea(new Dimension(10,0)));
		this.add(labelGebote);
		this.add(tfAnzGebote);
		this.add(Box.createRigidArea(new Dimension(10,0)));
		this.add(btnErstellen);
	}
	
	public String getArtikelName(){
		return tfArtikel.getText();
	}
	
	public String getVerkaufspreis(){
		return tfVerkaufspreis.getText();
	}
	
	public String getGebote(){
		return tfAnzGebote.getText();
	}
	
}
