package GUI_Panels;

import javax.swing.*;

import Controller.Controller;

public class ComboBoxPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public ComboBoxPanel(Controller controller) {

		String[] petStrings = { "Angebote", "Gebote", "Verkauft", "Gekauft", "Geladen"};

		JComboBox combo = new JComboBox(petStrings);
		combo.setSelectedItem(controller.getComboListe());
		combo.addActionListener(controller.comboAuktionenAL);
		add(combo);
	}
}
