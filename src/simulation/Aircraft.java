package simulation;

import java.util.ArrayList;
import java.util.List;

import model.Airport;
import model.Flight;

//avion koji leti
public class Aircraft {

	private static class Keyframe {

		final int time;
		final double x;
		final double y;

		Keyframe(int time, double x, double y) {
			this.time = time;
			this.x = x;
			this.y = y;
		}
	}

	private Flight flight;
	private final double speed;
	private List<Keyframe> path; // prvi-dep poslednji-arr
	private double x;
	private double y;
	private boolean active;
	private int lastSimulationTime; // poslednje vreme za koje je pozicija izracunata

	public Aircraft(Flight flight, int depTime) {
		this.flight = flight;

		Airport from = flight.getDepartureAirport();
		Airport to = flight.getArrivalAirport();
		int arrTime = depTime + flight.getDurationMinutes();

		this.path = new ArrayList<Aircraft.Keyframe>();
		path.add(new Keyframe(depTime, from.getX(), from.getY()));
		path.add(new Keyframe(arrTime, to.getX(), to.getY()));

		double distance = distance(from.getX(), from.getY(), to.getX(), to.getY());
		this.speed = flight.getDurationMinutes() > 0 ? distance / flight.getDurationMinutes() : 0;

		this.x = from.getX();
		this.y = from.getY();
		this.active = false;
		this.lastSimulationTime = depTime;
	}

	private static double distance(double x1, double y1, double x2, double y2) {
		double dx = x2 - x1;
		double dy = y2 - y1;
		return Math.sqrt(dx * dx + dy * dy);
	}

	public void updatePosition(int simulationTime) {
		lastSimulationTime = simulationTime;

		Keyframe first = path.get(0);
		Keyframe last = path.get(path.size() - 1);

		if (simulationTime < first.time) {
			active = false;
			return;
		}
		if (simulationTime >= last.time) {
			active = false;
			x = last.x;
			y = last.y;
			return;
		}
		active = true;

		for (int i = 0; i < path.size() - 1; i++) {
			Keyframe a = path.get(i);
			Keyframe b = path.get(i + 1);
			if (simulationTime >= a.time && simulationTime < b.time) { // vidimo u kom smo segmentu
				double progress = (double) (simulationTime - a.time) / (b.time - a.time);
				x = a.x + progress * (b.x - a.x);
				y = a.y + progress * (b.y - a.y);
				return;
			}
		}
	}

	public void reroute(List<double[]> waypoints, Airport destination) {
		List<Keyframe> newPath = new ArrayList<Aircraft.Keyframe>();
		newPath.add(new Keyframe(lastSimulationTime, x, y));

		double prevX = x;
		double prevY = y;
		int time = lastSimulationTime;

		for (double[] p : waypoints) {
			double segmentDistance = distance(prevX, prevY, p[0], p[1]);
			time += speed > 0 ? (int) Math.round(segmentDistance / speed) : 0;
			newPath.add(new Keyframe(time, p[0], p[1]));
			prevX = p[0];
			prevY = p[1];
		}

		double segmentDistance = distance(prevX, prevY, destination.getX(), destination.getY());
		time += speed > 0 ? (int) Math.round(segmentDistance / speed) : 0;
		newPath.add(new Keyframe(time, destination.getX(), destination.getY()));

		this.path = newPath;
		this.active = true;
	}

	// argumenti su od storma i proverava da li je trenutna pozicija unutar kruga
	public boolean isNear(double x, double y, double radius) {
		return distance(this.x, this.y, x, y) <= radius;
	}

	public Flight getFlight() {
		return flight;
	}

	public int getDepTime() {
		return path.get(0).time;
	}

	public int getArrTime() {
		return path.get(path.size() - 1).time;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public boolean isActive() {
		return active;
	}

}
