package GUI_Panels;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import Controller.Controller;

@SuppressWarnings("serial")
public class FooterDatenzugriff extends JPanel {
	
	private BoxLayout layout;
	private JButton speichern = new JButton("speichern");
	private JButton laden = new JButton("laden");

	
	public FooterDatenzugriff(Controller controller){
		
		layout  = new BoxLayout(this, BoxLayout.X_AXIS);
		setLayout(layout);
		
		speichern.addActionListener(controller.buttonSpeichern);
		laden.addActionListener(controller.buttonLaden);
		add(speichern);
		add(Box.createRigidArea(new Dimension(10,0)));
		add(laden);
	}

}
