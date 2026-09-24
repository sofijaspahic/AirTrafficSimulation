package simulation;

import java.util.HashMap;
import java.util.Map;

import model.Airport;
import model.Flight;

// na svakih 10 minuta jedan moze da poleti
public class AirportScheduler {

	private Map<Airport, Integer> nextAvailableDep = new HashMap<Airport, Integer>();

	public int schedule(Flight flight) {
		Airport from = flight.getDepartureAirport();
		int plannedDeparture = flight.getDepartureTimeMinutes();
		int nextAvailable;
		if (nextAvailableDep.containsKey(from)) {
			nextAvailable = nextAvailableDep.get(from);
		} else {
			nextAvailable = 0;
		}
		int realDeparture = plannedDeparture >= nextAvailable ? plannedDeparture : nextAvailable;
		nextAvailableDep.put(from, realDeparture + 10);
		return realDeparture;
	}

	public void resetHashMap() {
		nextAvailableDep.clear();
	}

}
