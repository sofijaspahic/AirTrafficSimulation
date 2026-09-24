package controller;

import gui.panels.MapPanel;
import gui.panels.SimulationPanel;
import model.AirTrafficModel;
import simulation.SimulationEngine;
import simulation.SimulationTimer;
import time.Timer;

public class SimulationController {

	private SimulationPanel simulationPanel;
	private Timer timer;
	private SimulationTimer timerSim;
	private MapPanel mapPanel;
	private SimulationEngine simulationEngine;
	private AirTrafficModel model;

	public SimulationController(SimulationPanel simulationPanel, Timer timer, MapPanel mapPanel,
			AirTrafficModel model) {
		this.simulationPanel = simulationPanel;
		this.timer = timer;
		this.mapPanel = mapPanel;
		this.model = model;
	}

	public void startSimulation() {
		timer.setPaused(true);
		simulationPanel.showSimulationTime();

		if (timerSim == null) {
			timerSim = new SimulationTimer(simulationPanel);
			timerSim.start();
		} else {
			timerSim.resumeTimer();
		}

		if (simulationEngine == null) {
			simulationEngine = new SimulationEngine(model.getFlights(), timerSim, mapPanel);
			simulationEngine.start();
		} else {
			simulationEngine.resumeSimulation();
		}
		mapPanel.setReroutingAllowed(false);
	}

	public void pauseSimulation() {
		if (timerSim != null)
			timerSim.pauseTimer();
		if (simulationEngine != null)
			simulationEngine.pauseSimulation();
		timer.setPaused(false); // simulacija je pauzirana, pa tajmer moze da radi opet
		mapPanel.setReroutingAllowed(true);
	}

	public void resetSimulation() {
		if (simulationEngine != null) {
			simulationEngine.stopSimulation();
			simulationEngine = null;
		}
		if (timerSim != null) {
			timerSim.stopTimer();
			timerSim = null;
		}
		mapPanel.clearAircrafts();
		simulationPanel.resetSimulationTime();
		timer.setPaused(false);
		mapPanel.setReroutingAllowed(false);
	}

	public void close() {
		if (simulationEngine != null)
			simulationEngine.stopSimulation();
		if (timerSim != null)
			timerSim.stopTimer();
	}

}
