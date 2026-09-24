package exceptions.flight;

public class InvalidFlightDurationException extends FlightException {

	private static final long serialVersionUID = 1L;

	public InvalidFlightDurationException() {
		super("Flight duration must be positive number.");
	}

}
