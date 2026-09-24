package exceptions.flight;

public class UnknownArrivalAirportException extends FlightException {

	private static final long serialVersionUID = 1L;

	public UnknownArrivalAirportException() {
		super("Arrival airport does not exist.");
	}

}
