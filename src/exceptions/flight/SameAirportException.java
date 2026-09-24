package exceptions.flight;

public class SameAirportException extends FlightException {

	private static final long serialVersionUID = 1L;

	public SameAirportException() {
		super("Departure and arrival airports cannot be the same.");
	}
	
}
