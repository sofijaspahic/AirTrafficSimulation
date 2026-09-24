package controller;

import java.awt.Frame;
import exceptions.ParseException;
import exceptions.airport.AirportException;
import gui.dialogs.ErrorDialog;
import gui.panels.AirportPanel;
import gui.panels.MapPanel;
import gui.tables.AirportTable;
import model.AirTrafficModel;
import model.Airport;

// izvrsi validaciju nad podacima iz AirportPanela, ako ne valjaju podaci salje signal za ErrorDialog 
// ako su ispravni, napravi se objekat klase Airport, ubaci se u AirTrafficModel i 
// naredi tabeli i mapi da azuriraju svoje podatke, tj. iscrtaju novi aerodrom
// updateuju dugmice za cuvanje fajlova

public class AirportController {

	private Frame owner;
	private AirTrafficModel airTrafficModel;
	private AirportPanel airportPanel;
	private AirportTable airportTable;
	private MapPanel mapPanel;
	private FileController fileController;

	public AirportController(Frame owner, AirTrafficModel model, AirportPanel airportPanel, AirportTable airportTable,
			MapPanel mapPanel, FileController fileController) {

		this.owner = owner;
		this.airTrafficModel = model;
		this.airportPanel = airportPanel;
		this.airportTable = airportTable;
		this.mapPanel = mapPanel;
		this.fileController = fileController;
	}

	public void addAirport() {
		try {
			int x, y;
			try {
				x = Integer.parseInt(airportPanel.getAirportCoordX());
				y = Integer.parseInt(airportPanel.getAirportCoordY());
			} catch (NumberFormatException e) {
				throw new ParseException("Coordinates must be valid integers.");
			}
			Airport airport = new Airport(airportPanel.getAirportName(), airportPanel.getAirportCode(), x, y);
			airTrafficModel.addAirport(airport);
			airportTable.addAirport(airport);
			mapPanel.setAirports(airTrafficModel.getAirports());
			fileController.updateSaveButtons();
			airportPanel.clearFields();
		} catch (ParseException | AirportException e) {
			ErrorDialog.showError(owner, e.getMessage());
		} finally {
			owner.validate();
			owner.repaint();
		}
	}
	
	
}
