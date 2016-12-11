package GUI_Panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.Controller;

@SuppressWarnings("serial")
public class HeaderPanel extends JPanel {

	private JPanel left = new JPanel();
	private JPanel center = new JPanel();
	private JPanel right = new JPanel();

	private JLabel text = new JLabel("");
	private JLabel log = new JLabel("");
	private JLabel status = new JLabel("");

	private JButton buttonHome = new JButton("MyMKIeBay");
	private JButton buttonLog = new JButton();
	private JButton buttonAuk = new JButton("Auktionen");
	private ImageIcon iconLog = new ImageIcon("logout.png");

	public HeaderPanel(Controller controller) {

		this.setLayout(new BorderLayout());

		center.setLayout(new GridLayout(1, 3));

		iconLog.setImage(iconLog.getImage().getScaledInstance(22, 22,
				Image.SCALE_DEFAULT));
		buttonLog.setIcon(iconLog);

		buttonHome.addActionListener(controller.buttonHome);
		buttonAuk.addActionListener(controller.buttonAuktionen);
		buttonLog.addActionListener(controller.buttonLogout);

		left.add(buttonHome);
		left.add(buttonAuk);
		center.add(status);
		center.add(text);
		center.add(log);
		right.add(buttonLog);

		this.add(left, BorderLayout.WEST);
		this.add(center, BorderLayout.CENTER);
		this.add(right, BorderLayout.EAST);

		this.setVisible(true);

	}

	public void setText(String s) {
		if (s != null) {
			text.setText(s);
		}
	}

	public void setLog(String s) {
		if (s != null) {
			log.setText(s);
		}
	}

	public void setStatus(String s) {
		if (s != null) {
			status.setText(s);
		}
	}

	public void usedButton(String s) {
		if (s.equals("home")) {
			buttonHome.setEnabled(false);
			buttonAuk.setEnabled(true);
		} else if (s.equals("auk")) {
			buttonAuk.setEnabled(false);
			buttonHome.setEnabled(true);
		} else if (s.equals("super")) {
			buttonAuk.setEnabled(false);
			buttonHome.setEnabled(false);
		}
	}

}