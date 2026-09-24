package exceptions.airport;

public class InvalidAirportYCoordException extends AirportException {

	private static final long serialVersionUID = 1L;

	public InvalidAirportYCoordException() {
		super("Airport Y coordinate must be between -90 and 90.");
	}

}
