package exceptions.flight;

public class UnknownDepartureAirportException extends FlightException {

	private static final long serialVersionUID = 1L;

	public UnknownDepartureAirportException() {
		super("Departure airport does not exist.");
	}

}
