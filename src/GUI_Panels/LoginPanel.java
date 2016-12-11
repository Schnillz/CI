package GUI_Panels;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Controller.Controller;

@SuppressWarnings("serial")
public class LoginPanel extends JPanel {

	private JButton loginButton = new JButton();
	private JTextField tfName = new JTextField();
	private JPasswordField pfPass = new JPasswordField();
	private JLabel textName = new JLabel();
	private JLabel textPass = new JLabel();
	private GridLayout layout = new GridLayout(3, 2);

	public LoginPanel(Controller controller) {

		this.setLayout(layout);

		textName.setText("Benutzername:");
		textPass.setText("Password:");
		loginButton.setText("Einloggen");
		tfName.setText("David");
		pfPass.setText("1234");
		
		this.add(textName);
		this.add(tfName);
		this.add(textPass);
		this.add(pfPass);
		this.add(loginButton);

		loginButton.addActionListener(controller.buttonLogin);
	}

	public String getName() {
		return tfName.getText();
	}

	@SuppressWarnings("deprecation")
	public String getPass() {
		return pfPass.getText();

	}
}