package gui.panels;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.TextListener;

// sadrzi tekstualna polja i nista vise, ne zna da li su podaci ispravni
// kada korisnik klikne na dugme, panel prosledjuje preko getera sirovi tekst kontroleru

public class AirportPanel extends Panel {

	private static final long serialVersionUID = 1L; // identifikator verzije klase koji java koristi prilikom serijalizacije

	private TextField name = new TextField(10);
	private TextField code = new TextField(3);
	private TextField coordX = new TextField(4);
	private TextField coordY = new TextField(3);
	private Button addA = new Button("Add airport");

	private static final Color TEXT_COLOR = new Color(20, 40, 80);

	private static final Color BCK_GND = new Color(210, 232, 250);
	private static final Color BTN = new Color(170, 205, 235);

	private static final Color ROW1 = new Color(173, 216, 255);
	private static final Color ROW2 = new Color(191, 226, 255);

	public AirportPanel() {
		addA.setEnabled(false);
		create();
		addTextListeners();
	}

	private void colorRow(String text, TextField field, Color color) {
		Panel row = new Panel(new GridLayout(1, 2, 3, 3));
		row.setBackground(color);
		Label label = new Label(text);
		label.setBackground(color);
		label.setForeground(TEXT_COLOR);

		field.setBackground(Color.WHITE);
		field.setForeground(TEXT_COLOR);
		row.add(label);
		row.add(field);
		add(row);
	}

	public void create() {
		setLayout(new GridLayout(0, 1, 0, 3));
		setBackground(BCK_GND);
		colorRow("Airport name: ", name, ROW2);
		colorRow("Airport code: ", code, ROW1);
		colorRow("X coordinate: ", coordX, ROW2);
		colorRow("Y coordinate: ", coordY, ROW1);

		addA.setPreferredSize(new Dimension(115, 30));
		addA.setBackground(BTN);
		addA.setForeground(TEXT_COLOR);
		Panel buttonPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(addA);
		add(buttonPanel);
	}

	public void clearFields() {
		name.setText("");
		code.setText("");
		coordX.setText("");
		coordY.setText("");
		updateButtonState();
	}

	private void updateButtonState() {
		boolean flag = !name.getText().trim().isEmpty() && !code.getText().trim().isEmpty()
				&& !coordX.getText().trim().isEmpty() && !coordY.getText().trim().isEmpty();
		addA.setEnabled(flag);
	}

	private void addTextListeners() {
		TextListener listener = e -> updateButtonState(); // pozivamo kad god se promeni nesto u poljima
		/*
		TextListener listener = new TextListener() {
		    @Override
		    public void textValueChanged(TextEvent e) {
		        updateButtonState();
		    }
		};
		*/
		name.addTextListener(listener);
		code.addTextListener(listener);
		coordX.addTextListener(listener);
		coordY.addTextListener(listener);
	}

	public void addActivityListener(TextListener listener) {
		name.addTextListener(listener);
		code.addTextListener(listener);
		coordX.addTextListener(listener);
		coordY.addTextListener(listener);
	}

	public void addAirportListener(ActionListener listener) {
		addA.addActionListener(listener);
	}

	public String getAirportName() {
		return name.getText();
	}

	public String getAirportCode() {
		return code.getText();
	}

	public String getAirportCoordX() {
		return coordX.getText();
	}

	public String getAirportCoordY() {
		return coordY.getText();
	}

}
