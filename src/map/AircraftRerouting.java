package map;

import java.util.ArrayList;
import java.util.List;

import model.Airport;
import simulation.Aircraft;

public class AircraftRerouting {

	public interface MapListener {
		void repaint();
	}

	private final MapListener callback;

	private Aircraft selected = null;
	private List<double[]> waypoints = new ArrayList<double[]>();
	private boolean enabled = false;

	public AircraftRerouting(MapListener callback) {
		this.callback = callback;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		if (!enabled) {
			cancel();
		}
	}

	public boolean isActive() {
		return selected != null;
	}

	public List<double[]> getWaypoints() {
		return waypoints;
	}

	public Aircraft getSelected() {
		return selected;
	}

	public void selectAircraft(Aircraft a) {
		if (!enabled)
			return;
		if (selected == a) {
			cancel();
		} else {
			selected = a;
			waypoints.clear();
			callback.repaint();
		}

	}

	public void addWaypoints(double x, double y) {
		if (!enabled || selected == null)
			return;
		waypoints.add(new double[] { x, y });
		callback.repaint();
	}

	public void finish(Airport destination) {
		if (!enabled || selected == null)
			return;
		selected.reroute(waypoints, destination);
		selected = null;
		waypoints.clear();
		callback.repaint();
	}

	public void cancel() {
		selected = null;
		waypoints.clear();
		callback.repaint();
	}

}
