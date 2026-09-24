package exceptions.flight;

public class InvalidDepartureTimeException extends FlightException {

	private static final long serialVersionUID = 1L;

	public InvalidDepartureTimeException() {
		super("Departure time must be in HH:mm format (00:00-23:59).");
	}
	
}
