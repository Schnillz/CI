package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import Controller.Controller;
import GUI_Panels.ComboBoxPanel;
import GUI_Panels.FooterAdmin;
import GUI_Panels.FooterAuktion;
import GUI_Panels.FooterDatenzugriff;
import GUI_Panels.HeaderPanel;
import GUI_Panels.LoginPanel;
import GUI_Panels.TableEbayPanel;
import GUI_Panels.TableEigenePanel;
import mKIebay_team.Benutzer;
import mKIebay_team.MKIebay;

public class GUI {

	private Benutzer b;

	private JFrame window;
	private JPanel mainPanel;
	private JPanel header;
	private JPanel footer;
	private JPanel center;
	private LoginPanel logPanel;
	private HeaderPanel headerPanel;
	private JPanel footerPanel;
	private Controller controller;
	private MKIebay ebay;

	private BorderLayout mainLayout = new BorderLayout();
	private BorderLayout headerLayout = new BorderLayout();
	private BorderLayout footerLayout = new BorderLayout();

	public LoginPanel getLogPanel() {
		return logPanel;
	}

	public JPanel getFooterPanel() {
		return footerPanel;
	}

	public GUI(final MKIebay ebay) {

		this.setMkiEbay(ebay);
		controller = new Controller(ebay, this);
		mainPanel = new JPanel(mainLayout);
		header = new JPanel(headerLayout);
		center = new JPanel();
		footer = new JPanel(footerLayout);

		window = new JFrame("MKIebay B4");

		headerPanel = new HeaderPanel(controller);
		header.add(headerPanel);

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(mainPanel);

		setContentLogin();
		window.setVisible(true);
	}

	private void setMkiEbay(MKIebay ebay) {
		if (ebay != null)
			this.ebay = ebay;
		else
			meldung("GUI Fehler", 2);
	}

	private void setBenutzer(Benutzer b2) {
		if (b2 == null) {
			meldung("GUI Fehler", 2);
		} else {
			this.b = b2;
		}
	}

	// Contentbereich Update

	public void setContentLogin() {

		mainPanel.removeAll();
		mainPanel.revalidate();

		center = new JPanel();

		if (logPanel == null)
			logPanel = new LoginPanel(controller);
		center.add(logPanel);

		mainPanel.add(center, BorderLayout.CENTER);
		window.setMinimumSize(new Dimension(10, 10));
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);

		// aufräumen
		b = null;
	}

	public void setContentEigene(Benutzer b2, String listenTyp) {
		if (b == null) {
			this.setBenutzer(b2);
			headerPanel.setLog("<html>eingeloggt als: " + b2);
			window.setSize(1000, 700);
			window.setMinimumSize(new Dimension(800, 400));
			window.setResizable(true);
			window.setLocationRelativeTo(null);
			headerPanel.setStatus("<html><center>Hallo " + b
					+ "<br> der Login war Erfolgreich</center></html>");
		} else {
			headerPanel.setStatus("");
		}

		if (b.equals(ebay.getSuperuser())) {
			setContentEbay();
		} else {
			try {
				setBasis(getListe(b2, listenTyp));
			} catch (Exception e) {
				throw new RuntimeException("abgebrochen");
			}

			headerPanel.setText("<html><font size=\"4\"> MyMKIeBay:  "
					+ listenTyp);
			headerPanel.usedButton("home");

			center.add(new ComboBoxPanel(controller), BorderLayout.NORTH);

			setFooter(false);
		}

	}

	public void setContentEbay() {
		headerPanel.setText("<html><font size=\"4\"> Laufende Auktionen");
		if (b.equals(ebay.getSuperuser())) {
			headerPanel.usedButton("super");
			headerPanel.setStatus("Eingeloggt als Superuser");
		} else {
			headerPanel.usedButton("auk");
			headerPanel.setStatus("");
		}
		setBasis(new TableEbayPanel(b.auktionen(), b, controller, ebay, false));
		setFooter(true);
	}

	private void setFooter(boolean auktionen) {
		if (b.equals(ebay.getSuperuser())) {
			footerPanel = new FooterAdmin(controller);
		} else if (auktionen) {
			footerPanel = new FooterAuktion(controller);
		} else {
			footerPanel = new FooterDatenzugriff(controller);
		}
		footer.add(footerPanel);
	}

	private TableEigenePanel getListe(Benutzer b2, String listenTyp) {
		if (listenTyp.equals("Geladen")) {
			try {
				File file = GUI.getFile(false);
				return new TableEigenePanel(b.laden(file), b2, controller, ebay);
			} catch (Exception e1) {
				throw new RuntimeException("Liste konnte nicht erstellt werden");
			}
		}
		if (listenTyp.equals("Angebote")) {
			return new TableEigenePanel(b2.angebote(), b2, controller, ebay);
		} else if (listenTyp.equals("Gebote")) {
			return new TableEigenePanel(b2.gebote(), b2, controller, ebay);
		} else if (listenTyp.equals("Verkauft")) {
			return new TableEigenePanel(b2.verkauft(), b2, controller, ebay);
		} else if (listenTyp.equals("Gekauft")) {
			return new TableEigenePanel(b2.gekauft(), b2, controller, ebay);
		}
		return null;
	}

	private void setBasis(Component tabPanel) {

		mainPanel.removeAll();
		mainPanel.revalidate();

		footer.removeAll();
		footer.revalidate();

		center = new JPanel(new BorderLayout());
		center.add(tabPanel, BorderLayout.CENTER);

		footer = new JPanel();

		mainPanel.add(header, BorderLayout.NORTH);
		mainPanel.add(footer, BorderLayout.SOUTH);
		mainPanel.add(center, BorderLayout.CENTER);
	}

	public static File getFile(boolean save) {
		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"CSV & SER Dateien", "csv", "ser");
		fc.setFileFilter(filter);
		if (save && fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			if (fc.getSelectedFile().getName().equals(".ser")
					|| fc.getSelectedFile().getName().equals(".csv")) {
				return null;
			}
			return new File(fc.getSelectedFile().toString());
		} else if (!save && fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			return new File(fc.getSelectedFile().toString());
		} else {
			return null;
		}
	}

	public static void meldung(String s, int x) {
		JPanel j = new JPanel();
		JOptionPane.showMessageDialog(j, s, "Information", x);
	}

}
