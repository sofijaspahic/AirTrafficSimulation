package controller;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import gui.dialogs.ErrorDialog;
import gui.panels.FilePanel;
import gui.panels.MapPanel;
import gui.tables.AirportTable;
import gui.tables.FlightTable;
import io.FileManager;
import model.AirTrafficModel;
import model.Airport;
import model.Flight;

// ucitava/cuva aerodrome i letove preko filemanagera(CSV/JSON)
// ako dodje do greske prosledjuje poruku ErrorDialog-u 
// ako uspe, ubaci se u AirTrafficModel i 
// naredi tabeli i mapi da azuriraju svoje podatke, tj. iscrtaju novi aerodrom
// updateuju dugmice za cuvanje fajlova (ima/nema podataka)

public class FileController {

	private Frame owner;
	private AirTrafficModel model;
	private AirportTable airportTable;
	private FlightTable flightTable;
	private MapPanel mapPanel;
	private FilePanel filePanel;

	public FileController(Frame owner, AirTrafficModel model, AirportTable airportTable, FlightTable flightTable,
			MapPanel mapPanel, FilePanel filePanel) {

		this.owner = owner;
		this.model = model;
		this.airportTable = airportTable;
		this.flightTable = flightTable;
		this.mapPanel = mapPanel;
		this.filePanel = filePanel;
	}

	public void loadFile(FileManager manager) {
		try {
			List<Airport> tempA = new ArrayList<>();
			List<Flight> tempF = new ArrayList<>();
			if (manager.loadD(owner, tempA, tempF)) {
				model.setData(tempA, tempF);
				refreshTables();
			}
		} catch (Exception e) {
			ErrorDialog.showError(owner, e.getMessage());
		}
	}

	public void saveFile(FileManager manager) {
		try {
			manager.saveD(owner, model.getAirports(), model.getFlights());
		} catch (Exception e) {
			ErrorDialog.showError(owner, e.getMessage());
		}
	}

	public void refreshTables() {
		airportTable.refreshTable(model.getAirports());
		flightTable.refreshTable(model.getFlights());
		mapPanel.setAirports(model.getAirports());
		updateSaveButtons();
		owner.validate();
		owner.repaint();
	}

	public void updateSaveButtons() {
		boolean hasData = !model.getAirports().isEmpty() || !model.getFlights().isEmpty();
		filePanel.updateSaveButtonState(hasData);
	}
}
