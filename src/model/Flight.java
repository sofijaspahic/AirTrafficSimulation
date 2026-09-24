package model;

import exceptions.flight.MissingArrivalAirportException;
import exceptions.flight.MissingDepartureAirportException;
import exceptions.flight.InvalidDepartureTimeException;
import exceptions.flight.InvalidFlightDurationException;
import exceptions.flight.SameAirportException;

// zapis o jednom letu, osnovni podaci
public class Flight {

	private Airport departureAirport;
	private Airport arrivalAirport;
	private int departureHour;
	private int departureMinute;
	private int duration; // in minutes

	public static void validate(Airport departureAirport, Airport arrivalAirport, int departureHour,
			int departureMinute, int duration) throws MissingArrivalAirportException, MissingDepartureAirportException,
			InvalidDepartureTimeException, InvalidFlightDurationException, SameAirportException {

		if (departureAirport == null)
			throw new MissingDepartureAirportException();
		if (arrivalAirport == null)
			throw new MissingArrivalAirportException();
		if (departureAirport.equals(arrivalAirport))
			throw new SameAirportException();
		if ((departureHour < 0 || departureHour >= 24) || (departureMinute < 0 || departureMinute >= 60))
			throw new InvalidDepartureTimeException();
		if (duration <= 0)
			throw new InvalidFlightDurationException();
	}

	public Flight(Airport departureAirport, Airport arrivalAirport, int departureHour, int departureMinute,
			int duration) throws MissingArrivalAirportException, MissingDepartureAirportException,
			InvalidDepartureTimeException, InvalidFlightDurationException, SameAirportException {
		validate(departureAirport, arrivalAirport, departureHour, departureMinute, duration);
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.departureHour = departureHour;
		this.departureMinute = departureMinute;
		this.duration = duration;
	}

	public int getDepartureTimeMinutes() {
		return departureHour * 60 + departureMinute;
	}

	public int getArrivalTimeMinutes() {
		return getDepartureTimeMinutes() + duration;
	}

	public String getFullDepartureTime() {
		return String.format("%02d:%02d", departureHour, departureMinute);
	}

	public Airport getDepartureAirport() {
		return departureAirport;
	}

	public Airport getArrivalAirport() {
		return arrivalAirport;
	}

	public int getDepartureHour() {
		return departureHour;
	}

	public int getDepartureMinute() {
		return departureMinute;
	}

	public int getDurationMinutes() {
		return duration;
	}

}
