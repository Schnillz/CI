package GUI_Panels;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Controller.Controller;

@SuppressWarnings("serial")
public class FooterAdmin extends JPanel{
	
	private BoxLayout layout;
	private JLabel title = new JLabel("Benutzer anlegen: ");
	private JLabel name = new JLabel("Name: ");
	private JLabel pw = new JLabel("Passwort: ");
	private JTextField tfName = new JTextField();
	private JPasswordField pfPass = new JPasswordField();
	private JButton erstellen = new JButton("Erstellen");
	
	public FooterAdmin(Controller controller){	
		
		layout= new BoxLayout(this, BoxLayout.X_AXIS);
		setLayout(layout);
		
		erstellen.addActionListener(controller.buttonErstellen);
		
		tfName.setPreferredSize(new Dimension(70,20));
		pfPass.setPreferredSize(new Dimension(70,20));
		
		add(title);
		this.add(Box.createRigidArea(new Dimension(20,0)));
		add(name);
		add(tfName);
		this.add(Box.createRigidArea(new Dimension(10,0)));
		add(pw);
		add(pfPass);
		this.add(Box.createRigidArea(new Dimension(10,0)));
		add(erstellen);
	}
	
	public JTextField getTfName() {
		return tfName;
	}

	public JPasswordField getPfPass() {
		return pfPass;
	}
}
