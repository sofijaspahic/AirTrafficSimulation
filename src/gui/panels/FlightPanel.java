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

//sadrzi tekstualna polja i nista vise, ne zna da li su podaci ispravni
//kada korisnik klikne na dugme, panel prosledjuje preko getera sirovi tekst kontroleru

public class FlightPanel extends Panel {

	private static final long serialVersionUID = 1L;
	private TextField departureCode = new TextField(3);
	private TextField arrivalCode = new TextField(3);
	private TextField departureM = new TextField(2);
	private TextField departureH = new TextField(2);
	private TextField duration = new TextField(6);
	private Button addF = new Button("Add flight");

	private static final Color TEXT_COLOR = new Color(20, 40, 80);

	private static final Color BCK_GND = new Color(210, 232, 250);
	private static final Color BTN = new Color(170, 205, 235);

	private static final Color ROW2 = new Color(191, 226, 255);
	private static final Color ROW1 = new Color(173, 216, 255);

	public FlightPanel() {
		addF.setEnabled(false);
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

	private void colorTimeRow(Color color) {
		Panel row = new Panel(new GridLayout(1, 2, 3, 3));
		row.setBackground(color);
		Label label = new Label("Departure time: ");
		label.setBackground(color);
		label.setForeground(TEXT_COLOR);

		Panel timePanel = new Panel(new FlowLayout(FlowLayout.CENTER));
		timePanel.setBackground(color);

		departureH.setBackground(Color.WHITE);
		departureH.setForeground(TEXT_COLOR);

		departureM.setBackground(Color.WHITE);
		departureM.setForeground(TEXT_COLOR);

		Label dots = new Label(":");
		dots.setBackground(color);
		dots.setForeground(TEXT_COLOR);

		timePanel.add(departureH);
		timePanel.add(dots);
		timePanel.add(departureM);

		row.add(label);
		row.add(timePanel);
		add(row);
	}

	public void create() {
		setLayout(new GridLayout(0, 1, 0, 3));
		setBackground(BCK_GND);
		colorRow("Departure airport: ", departureCode, ROW1);
		colorRow("Arrival airport: ", arrivalCode, ROW2);
		colorTimeRow(ROW1);
		colorRow("Flight duration: ", duration, ROW2);

		addF.setPreferredSize(new Dimension(115, 30));
		addF.setBackground(BTN);
		addF.setForeground(TEXT_COLOR);
		Panel buttonPanelF = new Panel(new FlowLayout(FlowLayout.CENTER));
		buttonPanelF.add(addF);
		add(buttonPanelF);

	}

	public void clearFields() {
		departureCode.setText("");
		arrivalCode.setText("");
		departureH.setText("");
		departureM.setText("");
		duration.setText("");
		updateButtonState();
	}

	private void updateButtonState() {
		boolean flag = !departureCode.getText().trim().isEmpty() && !arrivalCode.getText().trim().isEmpty()
				&& !departureH.getText().trim().isEmpty() && !departureM.getText().trim().isEmpty()
				&& !duration.getText().trim().isEmpty();
		addF.setEnabled(flag);
	}

	private void addTextListeners() {
		TextListener listener = e -> updateButtonState(); // call update method whenever text field changes
		departureCode.addTextListener(listener);
		arrivalCode.addTextListener(listener);
		departureH.addTextListener(listener);
		departureM.addTextListener(listener);
		duration.addTextListener(listener);
	}

	public void addActivityListener(TextListener listener) {
		departureCode.addTextListener(listener);
		arrivalCode.addTextListener(listener);
		departureH.addTextListener(listener);
		departureM.addTextListener(listener);
		duration.addTextListener(listener);
	}

	public void addFlightListener(ActionListener listener) {
		addF.addActionListener(listener);
	}

	public String getDepCode() {
		return departureCode.getText();
	}

	public String getArrCode() {
		return arrivalCode.getText();
	}

	public String getFlightDepH() {
		return departureH.getText();
	}

	public String getFlightDepM() {
		return departureM.getText();
	}

	public String getFlightDuration() {
		return duration.getText();
	}

}
