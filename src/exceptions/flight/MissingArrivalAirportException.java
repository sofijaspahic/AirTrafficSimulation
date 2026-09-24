package exceptions.flight;

public class MissingArrivalAirportException extends FlightException {

	private static final long serialVersionUID = 1L;

	public MissingArrivalAirportException() {
		super("Arrival airport code cannot be empty.");
	}

}
