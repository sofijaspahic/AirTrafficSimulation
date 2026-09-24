package controller;

import java.awt.Frame;

import exceptions.ParseException;
import exceptions.flight.FlightException;
import gui.dialogs.ErrorDialog;
import gui.panels.FlightPanel;
import gui.tables.FlightTable;
import model.AirTrafficModel;
import model.Flight;

// izvrsi validaciju nad podacima iz FlightPanela, ako ne valjaju podaci salje signal za ErrorDialog 
// ako su ispravni, kreira se let, ubaci se u AirTrafficModel i 
// naredi tabeli da azurira svoje podatke
// updateuju dugmice za cuvanje fajlova

public class FlightController {

	private Frame owner;
	private AirTrafficModel model;
	private FlightPanel flightPanel;
	private FlightTable flightTable;
	private FileController fileController;

	public FlightController(Frame owner, AirTrafficModel model, FlightPanel flightPanel, FlightTable flightTable,
			FileController fileController) {

		this.owner = owner;
		this.model = model;
		this.flightPanel = flightPanel;
		this.flightTable = flightTable;
		this.fileController = fileController;
	}

	public void addFlight() {
		try {
			int depH, depM, dur;
			try {
				depH = Integer.parseInt(flightPanel.getFlightDepH());
				depM = Integer.parseInt(flightPanel.getFlightDepM());
				dur = Integer.parseInt(flightPanel.getFlightDuration());
			} catch (NumberFormatException e) {
				throw new ParseException("Departure time and flight duration must be valid integers.");
			}
			Flight flight = model.createFlight(flightPanel.getDepCode(), flightPanel.getArrCode(), depH, depM, dur);
			model.addFlight(flight);
			flightTable.addFlight(flight);
			fileController.updateSaveButtons();
		} catch (ParseException | FlightException e) {
			ErrorDialog.showError(owner, e.getMessage());
		} finally {
			flightPanel.clearFields();
			owner.validate();
			owner.repaint();
		}
	}

}