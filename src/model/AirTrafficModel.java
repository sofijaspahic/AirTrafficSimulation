package model;

import java.util.ArrayList;
import java.util.List;
import exceptions.airport.DuplicateAirportCodeException;
import exceptions.flight.InvalidDepartureTimeException;
import exceptions.flight.InvalidFlightDurationException;
import exceptions.flight.MissingArrivalAirportException;
import exceptions.flight.MissingDepartureAirportException;
import exceptions.flight.SameAirportException;
import exceptions.flight.UnknownArrivalAirportException;
import exceptions.flight.UnknownDepartureAirportException;

// cuva listu aerodroma, kad god tabela/mapa treba da se iscrtaju, one odavde citaju podatke
// cuva listu letova, kad god tabela treba da se iscrta, ona odavde cita podatke, ima metodu createFlight koja se poziva iz kontrolera

public class AirTrafficModel {

	private List<Airport> airports = new ArrayList<Airport>();
	private List<Flight> flights = new ArrayList<Flight>();

	public void addAirport(Airport airport) throws DuplicateAirportCodeException {
		if (findAirport(airport.getCode()) != null)
			throw new DuplicateAirportCodeException();
		airports.add(airport); // dodajemo samo ako ga vec nema u listi
	}

	public void addFlight(Flight flight) {
		flights.add(flight);
	}

	public Flight createFlight(String depCode, String arrCode, int depH, int depM, int duration)
			throws UnknownDepartureAirportException, UnknownArrivalAirportException, MissingArrivalAirportException,
			MissingDepartureAirportException, InvalidDepartureTimeException, InvalidFlightDurationException,
			SameAirportException {
		Airport dep = findAirport(depCode);
		Airport arr = findAirport(arrCode);
		if (dep == null)
			throw new UnknownDepartureAirportException();
		if (arr == null)
			throw new UnknownArrivalAirportException();
		return new Flight(dep, arr, depH, depM, duration);
	}

	public List<Airport> getAirports() {
		return airports;
		// return Collections.unmodifiableList(airports);
	}

	public List<Flight> getFlights() {
		return flights;
	}

	private Airport findAirport(String code) {
		if (code == null)
			return null;
		for (Airport a : airports) {
			if (a.getCode().equals(code.trim())) { // trim() brise razmake na pocetku i kraju stringa
				return a;
			}
		}
		return null;
	}

	public void setData(List<Airport> newAirports, List<Flight> newFlights) {
		airports.clear();
		flights.clear();
		airports.addAll(newAirports);
		flights.addAll(newFlights);
	}

}
