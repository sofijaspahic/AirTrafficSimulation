package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import controller.ActivityTracker;
import controller.AirportController;
import controller.FileController;
import controller.FlightController;
import controller.SimulationController;
import gui.panels.AirportPanel;
import gui.panels.FilePanel;
import gui.panels.FlightPanel;
import gui.panels.MapPanel;
import gui.panels.SimulationPanel;
import gui.tables.AirportTable;
import gui.tables.FlightTable;
import io.CSVFormatter;
import io.FileManager;
import io.JSONFormatter;
import model.AirTrafficModel;
import time.Timer;

public class MainWindow extends Frame {

	private static final long serialVersionUID = 1L;
	private AirportPanel airportPanel = new AirportPanel();
	private FlightPanel flightPanel = new FlightPanel();
	private FilePanel filePanel = new FilePanel();
	private SimulationPanel simulationPanel = new SimulationPanel();
	private MapPanel mapPanel = new MapPanel();

	private AirTrafficModel model = new AirTrafficModel();
	private AirportTable airportTable = new AirportTable();
	private FlightTable flightTable = new FlightTable();

	private FileManager csvManager = new FileManager(new CSVFormatter());
	private FileManager jsonManager = new FileManager(new JSONFormatter());

	private ActivityTracker activityTracker;
	private FileController fileController;
	private AirportController airportController;
	private FlightController flightController;
	private SimulationController simulationController;
	private Timer timer;

	public MainWindow() {
		setFont(new Font("Serif", Font.PLAIN, 14));
		setBounds(70, 10, 1400, 800);
		setTitle("Air Traffic Simulator");
		airportTable.setMapPanel(mapPanel);
		populateWindow();
		timer = new Timer(simulationPanel.getTimerLabel(), this);
		mapPanel.setTimer(timer);

		createControllers();

		addListeners();
		activityTracker.addActivityListeners(this);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeAll();
			}
		});

		timer.start();
		setVisible(true);
	}

	private void createControllers() {
		activityTracker = new ActivityTracker(timer);
		fileController = new FileController(this, model, airportTable, flightTable, mapPanel, filePanel);
		airportController = new AirportController(this, model, airportPanel, airportTable, mapPanel, fileController);
		flightController = new FlightController(this, model, flightPanel, flightTable, fileController);
		airportTable.setActivityListener(activityTracker.mouseListener());
		flightTable.setActivityListener(activityTracker.mouseListener());
		simulationController = new SimulationController(simulationPanel, timer, mapPanel, model);
	}

	private void addListeners() {

		airportPanel.addAirportListener((ae) -> {
			activityTracker.userActivity();
			airportController.addAirport();
		});
		airportPanel.addActivityListener(e -> activityTracker.userActivity());

		flightPanel.addFlightListener((ae) -> {
			activityTracker.userActivity();
			flightController.addFlight();
		});
		flightPanel.addActivityListener(e -> activityTracker.userActivity());

		filePanel.addLoadCSVListener(e -> {
			activityTracker.userActivity();
			fileController.loadFile(csvManager);
		});
		filePanel.addSaveCSVListener(e -> {
			activityTracker.userActivity();
			fileController.saveFile(csvManager);
		});

		filePanel.addLoadJSONListener(e -> {
			activityTracker.userActivity();
			fileController.loadFile(jsonManager);
		});
		filePanel.addSaveJSONListener(e -> {
			activityTracker.userActivity();
			fileController.saveFile(jsonManager);
		});

		simulationPanel.addStartListener(e -> {
			activityTracker.userActivity();
			simulationController.startSimulation();
		});
		simulationPanel.addResetListener(e -> {
			activityTracker.userActivity();
			simulationController.resetSimulation();
		});
		simulationPanel.addPauseListener(e -> {
			activityTracker.userActivity();
			simulationController.pauseSimulation();
		});
		simulationPanel.addZoomInListener(e -> {
			activityTracker.userActivity();
			mapPanel.zoomIn();
		});
		simulationPanel.addZoomOutListener(e -> {
			activityTracker.userActivity();
			mapPanel.zoomOut();
		});
	}

	private void populateWindow() {

		Panel content = new Panel(new BorderLayout());
		Panel northPanel = new Panel(new GridLayout(1, 3));

		northPanel.add(airportPanel);
		northPanel.add(flightPanel);
		northPanel.add(filePanel);

		Panel tablesPanel = new Panel(new GridLayout(1, 2));

		Panel airportTablePanel = new Panel(new BorderLayout());
		airportTablePanel.setBackground(new Color(245, 250, 255));
		airportTablePanel.add(new Label("AIRPORTS", Label.CENTER), BorderLayout.NORTH);
		airportTablePanel.add(airportTable, BorderLayout.CENTER);

		Panel flightTablePanel = new Panel(new BorderLayout());
		flightTablePanel.setBackground(new Color(245, 250, 255));
		flightTablePanel.add(new Label("FLIGHTS", Label.CENTER), BorderLayout.NORTH);
		flightTablePanel.add(flightTable, BorderLayout.CENTER);

		tablesPanel.add(airportTablePanel);
		tablesPanel.add(flightTablePanel);

		content.add(northPanel, BorderLayout.NORTH);
		content.add(mapPanel, BorderLayout.CENTER);
		content.add(tablesPanel, BorderLayout.SOUTH);
		content.add(simulationPanel, BorderLayout.EAST);

		airportPanel.setBackground(new Color(210, 232, 250));
		flightPanel.setBackground(new Color(210, 232, 250));
		filePanel.setBackground(new Color(210, 232, 250));

		add(content);
		validate();
		repaint();
	}

	private void closeAll() {
		if (simulationController != null) {
			simulationController.close();
		}
		if (mapPanel != null) {
			mapPanel.stopBlinking();
		}
		if (timer != null) {
			timer.closeProgram();
		} else {
			dispose();
		}
	}

	public static void main(String[] args) {
		new MainWindow();
	}
}
