package exceptions.airport;

public class InvalidAirportXCoordException extends AirportException {

	private static final long serialVersionUID = 1L;

	public InvalidAirportXCoordException() {
		super("Airport X coordinate must be between -180 and 180.");
	}

}
