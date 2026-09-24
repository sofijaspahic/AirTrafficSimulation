package gui.panels;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionListener;

// kada korisnik klikne dugme, u mainwindow se registruju listeneri i onda zove iz kontrolera metode load/save file

public class FilePanel extends Panel {

	private static final long serialVersionUID = 1L;
	private Button loadCSV = new Button("Load CSV file");
	private Button saveCSV = new Button("Save CSV file");
	private Button loadJSON = new Button("Load JSON file");
	private Button saveJSON = new Button("Save JSON file");

	private static final Color TEXT_COLOR = new Color(20, 40, 80);

	private static final Color BCK_GND = new Color(210, 232, 250);
	private static final Color BTN = new Color(235,244,255);

	private static final Color ROW2 = new Color(185, 218, 245);
	private static final Color ROW1 = new Color(205, 232, 255);

	public FilePanel() {
		saveCSV.setEnabled(false);
		saveJSON.setEnabled(false);
		create();
	}

	public void create() {
		setLayout(new GridLayout(0, 1));
		setBackground(BCK_GND);
		Panel title = new Panel(new FlowLayout(FlowLayout.CENTER));
		title.setBackground(ROW2);
		Label titleLabel = new Label("Files");
		titleLabel.setBackground(ROW2);
		titleLabel.setForeground(TEXT_COLOR);
		title.add(titleLabel);
		add(title);
		addButton(loadCSV, ROW1);
		addButton(saveCSV, ROW2);
		addButton(loadJSON, ROW1);
		addButton(saveJSON, ROW2);
	}

	public void updateSaveButtonState(boolean enabled) {
		saveCSV.setEnabled(enabled);
		saveJSON.setEnabled(enabled);
	}

	public void addLoadCSVListener(ActionListener listener) {
		loadCSV.addActionListener(listener);
	}

	public void addSaveCSVListener(ActionListener listener) {
		saveCSV.addActionListener(listener);
	}

	public void addLoadJSONListener(ActionListener listener) {
		loadJSON.addActionListener(listener);
	}

	public void addSaveJSONListener(ActionListener listener) {
		saveJSON.addActionListener(listener);
	}

	public void addButton(Button btn, Color color) {
		Panel p = new Panel(new FlowLayout());
		p.setBackground(color);
		btn.setPreferredSize(new Dimension(200, 30));
		btn.setBackground(BTN);
		btn.setForeground(TEXT_COLOR);
		p.add(btn);
		add(p);
	}

}
