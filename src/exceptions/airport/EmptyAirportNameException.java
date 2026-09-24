package exceptions.airport;

public class EmptyAirportNameException extends AirportException {

	private static final long serialVersionUID = 1L;

	public EmptyAirportNameException() {
		super("Airport name cannot be empty.");
	}
	
}
