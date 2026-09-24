package gui.tables;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.event.MouseListener;
import java.util.List;

import model.Flight;

//uzima podatke iz liste koja je u klasi AirTrafficModel i osvezava tabelarni prikaz 

public class FlightTable extends ScrollPane {

	private static final long serialVersionUID = 1L;
	private Panel content = new Panel(new GridLayout(0, 4));
	private MouseListener activityListener;

	public FlightTable() {
		super();
		addColumnNames();
		add(content);
	}

	public void addColumnNames() {
		content.add(new Label("Departure airport", Label.CENTER));
		content.add(new Label("Arrival airport", Label.CENTER));
		content.add(new Label("Departure time", Label.CENTER));
		content.add(new Label("Flight duration", Label.CENTER));
	}

	public void setActivityListener(MouseListener listener) {
		this.activityListener = listener;
		attachActivity(content);
		for (Component c : content.getComponents()) {
			attachActivity(c);
		}
	}

	private void attachActivity(Component c) {
		if (activityListener != null) {
			c.addMouseListener(activityListener);
		}
	}

	public void drawFlightRow(Flight f) {
		Label depLabel = new Label(f.getDepartureAirport().getCode(), Label.CENTER);
		Label arrLabel = new Label(f.getArrivalAirport().getCode(), Label.CENTER);
		Label timeLabel = new Label(f.getFullDepartureTime(), Label.CENTER);
		Label durLabel = new Label(String.valueOf(f.getDurationMinutes()), Label.CENTER);
		attachActivity(depLabel);
		attachActivity(arrLabel);
		attachActivity(timeLabel);
		attachActivity(durLabel);

		content.add(depLabel);
		content.add(arrLabel);
		content.add(timeLabel);
		content.add(durLabel);
	}

	public void addFlight(Flight f) {
		drawFlightRow(f);
		content.validate();
		validate();
		repaint();
	}

	public void refreshTable(List<Flight> flights) {
		content.removeAll();
		addColumnNames();
		for (Flight f : flights) {
			drawFlightRow(f);
		}
		content.validate();
		validate();
		repaint();
	}

}