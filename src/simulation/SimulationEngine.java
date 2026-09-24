package simulation;

import java.util.ArrayList;
import java.util.List;

import gui.panels.MapPanel;
import model.Flight;

// kad se klikne na start, za svaki let pozove scheduler i napravi aircraft
// na svakih 200ms iscrta poziciju
public class SimulationEngine extends Thread {

	private List<Flight> flights;
	private List<Aircraft> aircrafts = new ArrayList<>();

	private AirportScheduler scheduler = new AirportScheduler();

	private SimulationTimer simulationTimer;
	private MapPanel mapPanel;

	private volatile boolean running = true;
	private volatile boolean paused = false;

	public SimulationEngine(List<Flight> flights, SimulationTimer simulationTimer, MapPanel mapPanel) {
		this.flights = flights;
		this.simulationTimer = simulationTimer;
		this.mapPanel = mapPanel;
		createAircrafts();
		mapPanel.setAircrafts(aircrafts);
	}

	private List<Flight> getSortedFlights() {
		List<Flight> sortedFlights = new ArrayList<Flight>(flights);
		sortedFlights.sort((f1, f2) -> Integer.compare(f1.getDepartureTimeMinutes(), f2.getDepartureTimeMinutes()));
		return sortedFlights;
	}

	private void createAircrafts() {

		aircrafts.clear();
		scheduler.resetHashMap();

		for (Flight flight : getSortedFlights()) {
			int depTime = scheduler.schedule(flight); // correct time
			aircrafts.add(new Aircraft(flight, depTime));
		}

	}

	// 1s 10 min 200ms 2min
	@Override
	public void run() {
		while (running) {
			if (!paused) {
				int simulationTime = simulationTimer.getMinutes();
				for (Aircraft a : aircrafts) {
					a.updatePosition(simulationTime);
				}
				mapPanel.render();
			}
			try {
				sleep(200);
			} catch (InterruptedException e) {
				return;
			}
		}
	}

	public List<Aircraft> getAircrafts() {
		return aircrafts;
	}

	public void stopSimulation() {
		running = false;
		interrupt();
	}

	public void pauseSimulation() {
		paused = true;
	}

	public void resumeSimulation() {
		paused = false;
	}

}
