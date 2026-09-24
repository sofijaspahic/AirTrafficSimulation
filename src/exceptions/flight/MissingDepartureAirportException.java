package exceptions.flight;

public class MissingDepartureAirportException extends FlightException {

	private static final long serialVersionUID = 1L;

	public MissingDepartureAirportException() {
		super("Departure airport code cannot be empty.");
	}

}
